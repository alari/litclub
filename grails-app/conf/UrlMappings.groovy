class UrlMappings {

  static mappings = {

    "/sbj/$domain/$action?/$id?" {
      controller = "subject"
    }

    "/$controller/$action?/$id?" {
      constraints {
        // apply constraints here
      }
    }

    "/"(view: "/index")
    "500"(view: '/error')
    "404"(view: "/404")
  }
}
