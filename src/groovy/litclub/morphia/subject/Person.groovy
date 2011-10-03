@Typed package litclub.morphia.subject

import com.google.code.morphia.annotations.Embedded

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 2:09 PM
 */
class Person extends Subject{

	private String password
  String email
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

  transient public boolean passwordChanged

  String getPassword(){
    password
  }
  void setPassword(String password) {
    this.password = password
    passwordChanged = true
  }
  void setPasswordHash(String hash) {
    password = hash
    passwordChanged = false
  }

  @Embedded Set<Role> authorities = []

  String toString(){
    "@"+domain
  }
}
