import litclub.morphia.node.NodeContentDAO
import litclub.morphia.node.NodeDAO
import litclub.morphia.linkage.SubjectLinkageBundleDAO
import litclub.morphia.talk.TalkDAO
import litclub.morphia.talk.TalkPhraseDAO
import litclub.morphia.RegistrationCodeDAO

// Place your Spring DSL code here
beans = {
  i18n(litclub.I18n)

  morphiaDriver(litclub.morphia.MorphiaDriver)
  nodeDao(NodeDAO)
  nodeContentDao(NodeContentDAO)
  subjectLinkageBundleDao(SubjectLinkageBundleDAO)
  talkDao(TalkDAO)
  talkPhraseDao(TalkPhraseDAO)
  registrationCodeDao(RegistrationCodeDAO)
}
