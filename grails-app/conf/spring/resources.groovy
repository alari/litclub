import litclub.morphia.node.NodeContentDAO
import litclub.morphia.node.NodeDAO
import litclub.morphia.linkage.SubjectLinkageBundleDAO
import litclub.morphia.talk.TalkDAO
import litclub.morphia.talk.TalkPhraseDAO
import litclub.morphia.sec.RegistrationCodeDAO
import litclub.morphia.subject.SubjectDAO
import litclub.morphia.MorphiaDriver
import litclub.I18n
import litclub.morphia.subject.SubjectInfoDAO
import litclub.UserDetailsService
import litclub.morphia.subject.PersonDAO
import litclub.RedisKeys

// Place your Spring DSL code here
beans = {
  i18n(I18n)

  morphiaDriver(MorphiaDriver)
  nodeDao(NodeDAO)
  nodeContentDao(NodeContentDAO)
  subjectLinkageBundleDao(SubjectLinkageBundleDAO)
  talkDao(TalkDAO)
  talkPhraseDao(TalkPhraseDAO)
  registrationCodeDao(RegistrationCodeDAO)

  subjectDao(SubjectDAO)
  subjectInfoDao(SubjectInfoDAO)
  personDao(PersonDAO)

  userDetailsService(UserDetailsService)
  redisKeys(RedisKeys)
}
