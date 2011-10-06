package pages

import geb.Page
import geb.navigator.Navigator

/**
 * @author Dmitry Kurinskiy
 * @since 10/3/11 5:11 PM
 */
class RootPage extends Page{
  static url = ""
  static at = { $("a.skip").size() == 1 }

  static content = {
    topNav {$(".nav").find("li")}
    signInOut {$(".nav").find("li a", 1)}
  }
}
