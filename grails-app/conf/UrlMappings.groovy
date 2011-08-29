class UrlMappings {

  static mappings = {

    "/admin/cfDashboard/$action?"(controller: 'cloudFoundryDashboard')

    "/admin/cfDashboard/application/$appName"(controller: 'cloudFoundryDashboard', action: 'application')

    "/admin/cfDashboard/service/$serviceName"(controller: 'cloudFoundryDashboard', action: 'service')

    "/admin/cfDashboard/files/$appName/$instanceIndex?"(controller: 'cloudFoundryDashboard', action: 'files')

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
