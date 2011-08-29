package litclub.validators

/**
 * @author Dmitry Kurinskiy
 * @since 27.08.11 22:10
 */
class PasswordValidators {
    static final passwordValidator = { String password, command ->
    if (command.domain && command.domain.equals(password)) {
      return 'command.password.error.username'
    }

    if (password && password.length() >= 8 && password.length() <= 64 &&
        (!password.matches('^.*\\p{Alpha}.*$') ||
            !password.matches('^.*\\p{Digit}.*$') ||
            !password.matches('^.*[!@#$%^&].*$'))) {
      return 'command.password.error.strength'
    }
  }

  static final password2Validator = { value, command ->
    if (command.password != command.password2) {
      return 'command.password2.error.mismatch'
    }
  }
}
