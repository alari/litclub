dataSource {
  pooled = true
/*    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""*/
}
hibernate {
  cache.use_second_level_cache = true
  cache.use_query_cache = true
  cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
  development {
    dataSource {
      dbCreate = "update"//"create-drop"
      url = 'jdbc:mysql://localhost/litclub'
      driverClassName = 'com.mysql.jdbc.Driver'
      dialect = org.hibernate.dialect.MySQL5InnoDBDialect
    }
  }
  test {
    dataSource {
      dbCreate = "update"
      url = "jdbc:h2:mem:testDb"
    }
  }
  production {
    dataSource {
      dbCreate = "update"
      url = "jdbc:h2:prodDb"
    }
  }
}
