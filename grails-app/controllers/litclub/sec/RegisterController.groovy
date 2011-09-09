package litclub.sec

import litclub.ServiceResponse

class RegisterController {

  static defaultAction = 'index'

  def registrationService

  def index = {
    render view: '/register/index', model: [command: new RegisterCommand()]
  }

  def register = {RegisterCommand command ->
    def model = registrationService.handleRegistration(command).ok ? [emailSent: true] : [command: command]
    render view: '/register/index', model: model
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

    if(!result.ok) {
      if(result.messageCode) flash.error = message(code: result.messageCode)
      if(result.redirectUri) {
        redirect uri: result.redirectUri
      } else {
        render view: "/register/resetPassword", model: result.model
      }
    } else {
      if(result.messageCode) flash.message = message(code: result.messageCode)
      redirect uri: result.redirectUri
    }
  }
}



