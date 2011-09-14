import litclub.morphia.NodeType

class UrlMappings {

  static mappings = {

    def domainCheck = '^[-_a-zA-Z0-9]{4,16}$'

    "/$domain/" {
      constraints {
        domain matches: domainCheck
      }
      controller = "subject"
    }
    "/$domain/add.$type" {
        constraints {
          domain matches: domainCheck
          type inList: NodeType.values().collect {it.toString()}
        }
      controller = "subject"
      action = "addNode"
    }
    "/$domain/$type" {
      constraints {
          domain matches: domainCheck
          type inList: NodeType.values().collect {it.toString()}
        }
      controller = "subject"
      action = "typeList"
    }

    "/own.talks/$id?" {
      constraints {
        id matches: '^[0-9]+$'
      }
      controller = "talks"
      action = "talk"
    }

    "/own.talks/$action?" {
      controller = "talks"
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
