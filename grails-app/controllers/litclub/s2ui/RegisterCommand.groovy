package litclub.s2ui

import litclub.Subject
import litclub.validators.PasswordValidators

/**
 * @author Dmitry Kurinskiy
 * @since 18.08.11 23:02
 */
class RegisterCommand {
  String domain
  String email
  String password
  String password2

  static constraints = {
    domain blank: false, validator: { value, command ->
      if (value) {
        if (Subject.countByDomain(value)) {
          return 'registerCommand.domain.unique'
        }
        if (!((String) value).matches('^[-_a-zA-Z0-9]{4,16}$')) {
          return "bad domain"
        }
      }
    }
    email blank: false, email: true
    password blank: false, minSize: 8, maxSize: 64, validator: PasswordValidators.passwordValidator
    password2 validator: PasswordValidators.password2Validator
  }
}
