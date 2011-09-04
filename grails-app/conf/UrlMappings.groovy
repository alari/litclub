class UrlMappings {

  static mappings = {

    "/$domain/$action?/$id?" {
      constraints {
        domain matches: '^[-_a-zA-Z0-9]{4,16}$'
      }
      controller = "subject"
    }

    "/x.$controller/$action?/$id?" {
      constraints {
        // apply constraints here
      }
    }

    "/"(view: "/index")
    "500"(view: '/error')
    "404"(view: "/404")
  }
}
