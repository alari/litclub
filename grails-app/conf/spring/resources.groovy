// Place your Spring DSL code here
beans = {
  i18n(litclub.I18n)

  morphiaDriver(litclub.morphia.MorphiaDriver)
  nodeDao(litclub.morphia.dao.NodeDAO)
  nodeContentDao(litclub.morphia.dao.NodeContentDAO)
  participationDao(litclub.morphia.dao.ParticipationDAO)
}
