package pages

import geb.Page

/**
 * @author Dmitry Kurinskiy
 * @since 31.08.11 13:11
 */
class RegisterPage extends Page{
  static url = "register"

  static content = {
    domainInput { $("input[name=domain]") }
    emailInput { $("input[name=email]") }
    pwdInput {$("input[name=password]")}
    pwd2Input {$("input[name=password2]")}
    submit{$("input[type=submit]")}
  }
}
