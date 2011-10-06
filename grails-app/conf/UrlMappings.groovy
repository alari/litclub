import litclub.morphia.node.NodeType

class UrlMappings {

  static mappings = {

    Map domainCheck = [matches:'^[-_a-zA-Z0-9]{4,16}$']
    List nodeTypes = NodeType.values().collect {it.toString()}
    Map nodeCheck = [matches:'^[-_a-zA-Z0-9]{2,32}$', validator: {!nodeTypes.contains(it)}]

    "/$domain/" {
      constraints {
        domain domainCheck
      }
      controller = "subject"
    }
    "/$domain/add.$type" {
        constraints {
          domain domainCheck
          type inList: nodeTypes
        }
      controller = "subjectNode"
      action = "addNode"
    }
    "/$domain/$type" {
      constraints {
          domain domainCheck
          type inList: nodeTypes
        }
      controller = "subject"
      action = "typeList"
    }
    "/$domain/$node" {
      constraints {
        domain domainCheck
        node nodeCheck
      }
      controller = "subject"
      action = "node"
    }
    "/$domain/$node/$action" {
      constraints {
        domain domainCheck
        node nodeCheck
      }
      controller = "subjectNode"
    }
    "/$domain/adm.$action" {
      constraints {
        domain domainCheck
      }
      controller = "subjectAdm"
    }


    "/own.talks/$id?" {
      constraints {
        id matches: '^[a-z0-9]{24,24}$'
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
