package litclub.s2ui

import litclub.Person
import litclub.RegistrationCode
import litclub.ServiceResponse
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

class RegisterController extends litclub.s2ui.AbstractS2UiController {

  static defaultAction = 'index'

  def registrationService

  def index = {
    render view: '/s2ui/register/index', model: [command: new RegisterCommand()]
  }

  def register = {RegisterCommand command ->
    def model = registrationService.handleRegistration(command).ok ? [emailSent: true] : [command: command]
    render view: '/s2ui/register/index', model: model
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

    if (!request.method.equalsIgnoreCase("POST")) {
      // show the form
      render view: "/s2ui/register/forgotPassword"
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

    render view: "/s2ui/register/forgotPassword", model: [emailSent: result.ok]
  }

  def resetPassword = { ResetPasswordCommand command ->

    String token = params.t

    ServiceResponse result = registrationService.handleResetPassword(token, command, request.method)

    if(!result.ok) {
      if(result.messageCode) flash.error = message(code: result.messageCode)
      if(result.redirectUri) {
        redirect uri: result.redirectUri
      } else {
        render view: "/s2ui/register/resetPassword", model: result.model
      }
    } else {
      if(result.messageCode) flash.message = message(code: result.messageCode)
      redirect uri: result.redirectUri
    }
  }
}



