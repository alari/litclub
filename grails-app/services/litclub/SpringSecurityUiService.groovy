/* Copyright 2009-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package litclub

import java.text.SimpleDateFormat

/**
 * Helper methods for UI management.
 *
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class SpringSecurityUiService {

	static final String DATE_FORMAT = 'd MMM yyyy HH:mm:ss'

	static transactional = true

	def grailsApplication

	boolean updatePersistentLogin(persistentLogin, newProperties) {
		if (newProperties.lastUsed && newProperties.lastUsed instanceof String) {
			Calendar c = Calendar.instance
			c.time = new SimpleDateFormat(DATE_FORMAT).parse(newProperties.lastUsed)
			persistentLogin.lastUsed = c.time
		}

		if (newProperties.token) {
			persistentLogin.token = newProperties.token
		}

		persistentLogin.save()
		return !persistentLogin.hasErrors()
	}

	void deletePersistentLogin(persistentLogin) {
		persistentLogin.delete()
	}

	boolean updateRegistrationCode(registrationCode, String username, String token) {
		registrationCode.token = token
		registrationCode.username = username
		registrationCode.save()
		return !registrationCode.hasErrors()
	}

	void deleteRegistrationCode(registrationCode) {
		registrationCode.delete()
	}
}
