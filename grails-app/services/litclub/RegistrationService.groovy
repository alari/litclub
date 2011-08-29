package litclub

import litclub.s2ui.RegisterCommand
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import groovy.text.SimpleTemplateEngine
import grails.gsp.PageRenderer
import litclub.s2ui.ResetPasswordCommand

class RegistrationService {
  def saltSource
  def springSecurityService
  def mailService
  LinkGenerator grailsLinkGenerator
  PageRenderer groovyPageRenderer

  ServiceResponse handleRegistration(RegisterCommand command) {
    if (command.hasErrors()) {
      return new ServiceResponse(ok: false)
    }

    String salt = saltSource instanceof NullSaltSource ? null : command.domain
    String password = springSecurityService.encodePassword(command.password, salt)

    Person user = new Person(email: command.email, domain: command.domain,
        password: password, accountLocked: true, enabled: true)

    if (!user.validate() || !user.save()) {
      return new ServiceResponse(ok: false)
      // TODO
    }

    RegistrationCode registrationCode = new RegistrationCode(domain: user.domain).save()

    sendRegisterEmail(user, registrationCode.token)
    return new ServiceResponse(ok: true)
  }

  ServiceResponse verifyRegistration(String token) {
    ServiceResponse result = new ServiceResponse(redirectUri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl)

     def conf = SpringSecurityUtils.securityConfig

    def registrationCode = token ? RegistrationCode.findByToken(token) : null
    if (!registrationCode) {
      return result.setAttributes(ok:false, messageCode:"spring.security.ui.register.badCode")
    }

    Person user
    RegistrationCode.withTransaction { status ->
      user = Person.findByDomain(registrationCode.domain)
      if (!user) {
        return result.setAttributes(ok:false, messageCode:"spring.security.ui.register.userNotFound")
      }

      user.accountLocked = false
      user.save()
      for (roleName in conf.ui.register.defaultRoleNames) {
        PersonRole.create user, roleName.toString()
      }
      registrationCode.delete()
    }
    if(result.messageCode) {
      return result
    }

    if (!user) {
      return result.setAttributes(ok:false, messageCode:"spring.security.ui.register.badCode")
    }

    springSecurityService.reauthenticate user.domain

    return result.setAttributes(
        ok: true,
        messageCode:"spring.security.ui.register.complete",
        redirectUri: conf.ui.register.postRegisterUrl ?: result.redirectUri
       )
  }

  ServiceResponse handleForgotPassword(String domain) {
    ServiceResponse response = new ServiceResponse()
    if (!domain) {
      return response.setAttributes(ok: false, messageCode: 'spring.security.ui.forgotPassword.username.missing')
    }

    Person user = Person.findByDomain(domain)
    if (!user) {
      return response.setAttributes(ok: false, messageCode: 'spring.security.ui.forgotPassword.user.notFound')
    }

    RegistrationCode registrationCode = new RegistrationCode(domain: user.domain).save()

    return response.setAttributes(ok: sendForgotPasswordEmail(user, registrationCode.token))
  }

  ServiceResponse handleResetPassword(String token, ResetPasswordCommand command, String requestMethod) {
    def registrationCode = token ? RegistrationCode.findByToken(token) : null
    if (!registrationCode) {
      return new ServiceResponse(
          messageCode: 'spring.security.ui.resetPassword.badCode',
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
        messageCode: 'spring.security.ui.resetPassword.success',
        redirectUri: conf.ui.register.postResetUrl ?: conf.successHandler.defaultTargetUrl)
  }

  private  String evaluate(s, binding) {
		new SimpleTemplateEngine().createTemplate(s).make(binding)
	}

  private boolean sendRegisterEmail(Person person, String token) {
    String url = grailsLinkGenerator.link(absolute: true,
        controller: "register", action: 'verifyRegistration', params: [t: token])

    ConfigObject conf = SpringSecurityUtils.securityConfig

    String body = url//groovyPageRenderer.render(view:"/error", model:[user: person, url: url])

    mailService?.sendMail {
      to person.email
      from conf.ui.register.emailFrom.toString()
      subject conf.ui.register.emailSubject.toString()
      html body
    } ?: System.out.println(body.toString())
    true
  }

  private boolean sendForgotPasswordEmail(Person person, String token){
    String url = grailsLinkGenerator.link(absolute: true,
        controller: "register", action: 'resetPassword', params: [t: token])

    def conf = SpringSecurityUtils.securityConfig
    def body = conf.ui.forgotPassword.emailBody
    if (body.contains('$')) {
      body = evaluate(body, [user: person, url: url])
    }
    mailService?.sendMail {
      to person.email
      from conf.ui.forgotPassword.emailFrom
      subject conf.ui.forgotPassword.emailSubject
      html body.toString()
    } ?: System.out.println(body.toString())
    true
  }
}
