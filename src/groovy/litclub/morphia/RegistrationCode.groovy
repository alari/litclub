package litclub.morphia

import org.bson.types.ObjectId
import com.google.code.morphia.annotations.Id
import com.google.code.morphia.annotations.Indexed

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 1:24 PM
 */
class RegistrationCode {
  @Id ObjectId id

  String domain

  @Indexed
	String token = UUID.randomUUID().toString().replaceAll('-', '')

	Date dateCreated = new Date()
}
