@Typed package litclub

import org.springframework.context.i18n.LocaleContextHolder as LCH
import org.springframework.context.MessageSource
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Dmitry Kurinskiy
 * @since 06.04.11 14:40
 */
class I18n {
  // Usage: i18n."code(:locale)?"([value, value?, ...]?, "Default message"?, "encode as"?)

  @Autowired
  MessageSource messageSource

  String methodMissing(String code, args) {

    // Defining locale and message code
    def locale = LCH.getLocale()
    // TODO: get locale code from message

    // Preparing things we'll get from args
    String defaultMessage = code
    String encodeAs = null

    args = args as List

    // No arguments given
    if (args.size() == 0) {
      args = []
    } else {
      List params
      // There is a map of args and some unmapped params
      if (args[0] instanceof List) {
        params = args.tail()
        args = args[0]
      } else {
        // Only unmapped params
        params = args
        args = []
      }
      if (params.size()) {
        if (params[0]) defaultMessage = params[0]
        if (params.size() > 1) {
          encodeAs = params[1].toString().toUpperCase()
        }
      }
    }

    String text = messageSource.getMessage(code, args as Object[],
        defaultMessage, locale) ?: defaultMessage

    if (text) {
      return text
      //TODO return encodeAs ? text."encodeAs${encodeAs}"() : text
    }
    ''
  }

  String propertyMissing(String code) {
    methodMissing(code, [])
  }

  String m(String code) {
    propertyMissing code
  }
}