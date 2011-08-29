@Typed package litclub

import org.springframework.context.ApplicationContext
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Dmitry Kurinskiy
 * @since 19.08.11 13:44
 */
class ServiceResponse {
  boolean ok
  String messageCode
  String redirectUri
  Map<String,Object> model

  @Autowired
  I18n i18n

  ServiceResponse(){}

  /**
   * @attr ok
   * @param attributes
   */
  ServiceResponse(Map attributes) {
    setAttributes(attributes)
  }

  ServiceResponse setAttributes(Map attributes) {
    if(attributes.containsKey("ok")) ok = attributes.ok
    if(attributes.containsKey("messageCode")) messageCode = attributes.messageCode
    if(attributes.containsKey("redirectUri")) redirectUri = attributes.redirectUri
    if(attributes.containsKey("model")) model = attributes.model
    this
  }
}
