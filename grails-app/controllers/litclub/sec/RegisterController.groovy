package litclub.sec

import litclub.ServiceResponse
import grails.plugins.springsecurity.Secured
import litclub.validators.PasswordValidators
import litclub.SubjectDomainService
import org.springframework.beans.factory.annotation.Autowired

@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
class RegisterController {

  static defaultAction = 'index'

  def registrationService

  def index = {
    render view: '/register/index', model: [command: new RegisterCommand()]
  }

  def register = {RegisterCommand command ->
    render view: '/register/index', model: registrationService.handleRegistration(command).model
  }

  def verifyRegistration = {
    String token = params.t
    ServiceResponse result = registrationService.verifyRegistration(token)

    if (result.ok) {
      flash.message = message(code: result.messageCode)
    } else {
      flash.error = message(code: result.messageCode)
    }

    redirect uri: result.redirectUri
  }

  def forgotPassword = {

    if (!request.post) {
      // show the form
      render view: "/register/forgotPassword"
      return
    }

    ServiceResponse result = registrationService.handleForgotPassword(params.domain)
    if (result.messageCode) {
      if (result.ok) {
        flash.message = message(code: result.messageCode)
      } else {
        flash.error = message(code: result.messageCode)
      }
    }

    render view: "/register/forgotPassword", model: [emailSent: result.ok]
  }

  def resetPassword = { ResetPasswordCommand command ->

    String token = params.t

    ServiceResponse result = registrationService.handleResetPassword(token, command, request.method)

    if (!result.ok) {
      if (result.messageCode) flash.error = message(code: result.messageCode)
      if (result.redirectUri) {
        redirect uri: result.redirectUri
      } else {
        render view: "/register/resetPassword", model: result.model
      }
    } else {
      if (result.messageCode) flash.message = message(code: result.messageCode)
      redirect uri: result.redirectUri
    }
  }
}



/**
 * @author Dmitry Kurinskiy
 * @since 18.08.11 23:02
 */
class RegisterCommand {

  @Autowired
  SubjectDomainService subjectDomainService

  String domain
  String email
  String password
  String password2

  static constraints = {
    domain blank: false, validator: { value, command ->
      if (value) {
        if (command.subjectDomainService.checkDomainExists(value)) {
          return 'registerCommand.domain.unique'
        }
        if (!((String) value).matches('^[-_a-zA-Z0-9]{4,16}$')) {
          return "registerCommand.domain.invalid"
        }
      }
    }
    email blank: false, email: true
    password blank: false, minSize: 8, maxSize: 64, validator: PasswordValidators.passwordValidator
    password2 validator: PasswordValidators.password2Validator
  }
}
