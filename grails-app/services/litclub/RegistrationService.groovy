package litclub

import litclub.sec.RegisterCommand
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource

import groovy.text.SimpleTemplateEngine

import litclub.sec.ResetPasswordCommand

class RegistrationService {
  def springSecurityService
  def mailSenderService
  def subjectDomainService
  def i18n

  ServiceResponse handleRegistration(RegisterCommand command) {
    if (command.hasErrors()) {
      return new ServiceResponse(ok: false)
    }

    Person user = new Person(email: command.email, domain: command.domain,
        password: command.password, accountLocked: true, enabled: true, info: new SubjectInfo())

    if (!user.validate() || !user.save(flush: true)) {
      return new ServiceResponse(ok: false, messageCode: user.errors)
      // TODO
    }

    RegistrationCode registrationCode = new RegistrationCode(domain: user.domain).save()

    // TODO: move it to GORM
    subjectDomainService.setDomain(user.id, user.domain)
    sendRegisterEmail(user, registrationCode.token)
    return new ServiceResponse(ok: true)
  }

  ServiceResponse verifyRegistration(String token) {
    ServiceResponse result = new ServiceResponse(redirectUri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl)

    def conf = SpringSecurityUtils.securityConfig

    def registrationCode = token ? RegistrationCode.findByToken(token) : null
    if (!registrationCode) {
      return result.setAttributes(ok: false, messageCode: "register.error.badCode")
    }

    Person user
    RegistrationCode.withTransaction { status ->
      user = Person.findByDomain(registrationCode.domain)
      if (!user) {
        return result.setAttributes(ok: false, messageCode: "register.error.userNotFound")
      }

      user.accountLocked = false
      if (!user.save(flush: true)) {
        log.error "Cannot save user: " + user.errors
        return result.setAttributes(ok: false, messageCode: "dont know what")
      }
      for (roleName in conf.ui.register.defaultRoleNames) {
        PersonRole.create user, roleName.toString()
      }
      registrationCode.delete()
    }
    if (result.messageCode) {
      return result
    }

    if (!user) {
      return result.setAttributes(ok: false, messageCode: "register.error.badCode")
    }

    springSecurityService.reauthenticate user.domain

    return result.setAttributes(
        ok: true,
        messageCode: "register.complete",
        redirectUri: conf.ui.register.postRegisterUrl ?: result.redirectUri
    )
  }

  ServiceResponse handleForgotPassword(String domain) {
    ServiceResponse response = new ServiceResponse()
    if (!domain) {
      return response.setAttributes(ok: false, messageCode: 'register.forgotPassword.username.missing')
    }

    Person user = Person.findByDomain(domain)
    if (!user) {
      return response.setAttributes(ok: false, messageCode: 'register.forgotPassword.user.notFound')
    }

    RegistrationCode registrationCode = new RegistrationCode(domain: user.domain).save()

    return response.setAttributes(ok: sendForgotPasswordEmail(user, registrationCode.token))
  }

  ServiceResponse handleResetPassword(String token, ResetPasswordCommand command, String requestMethod) {
    def registrationCode = token ? RegistrationCode.findByToken(token) : null
    if (!registrationCode) {
      return new ServiceResponse(
          messageCode: 'register.resetPassword.badCode',
          ok: false,
          redirectUri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl)
    }

    if (!requestMethod.equalsIgnoreCase("post")) {
      return new ServiceResponse(ok: false, model: [token: token, command: new ResetPasswordCommand()])
    }

    command.domain = registrationCode.domain
    command.validate()

    if (command.hasErrors()) {
      return new ServiceResponse(ok: false, model: [token: token, command: command])
    }

    RegistrationCode.withTransaction { status ->
      def user = Person.findByDomain(registrationCode.domain)
      user.password = command.password
      user.save()
      registrationCode.delete()
    }

    springSecurityService.reauthenticate registrationCode.domain

    def conf = SpringSecurityUtils.securityConfig
    return new ServiceResponse(
        ok: true,
        messageCode: 'register.resetPassword.success',
        redirectUri: conf.ui.register.postResetUrl ?: conf.successHandler.defaultTargetUrl)
  }

  private boolean sendRegisterEmail(Person person, String token) {
    mailSenderService.putMessage(
        to: person.email,
        subject: i18n."register.confirm.emailSubject",
        view: "/mail-messages/confirmEmail",
        model: [personId: person.id, token: token]
    )
    true
  }

  private boolean sendForgotPasswordEmail(Person person, String token) {
    mailSenderService.putMessage(
        to: person.email,
        subject: i18n."register.forgotPassword.emailSubject",
        view: "/mail-messages/forgotPassword",
        model: [personId: person.id, token: token]
    )
    true
  }
}
