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
package litclub.s2ui

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.security.core.context.SecurityContextHolder

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class SecurityInfoController {

	def accessDecisionManager
	def authenticationManager
	def channelProcessingFilter
	def logoutFilter
	def logoutHandlers
	def objectDefinitionSource
	def springSecurityFilterChain
	def userCache

	def index = {}

	def config = {
		render view: "/s2ui/securityInfo/config", model: [conf: new TreeMap(SpringSecurityUtils.securityConfig.flatten())]
	}

	def mappings = {
		// Map<Object, Collection<ConfigAttribute>>
		render view: "/s2ui/securityInfo/mappings", model: [configAttributeMap: new TreeMap(objectDefinitionSource.configAttributeMap),
		 securityConfigType: SpringSecurityUtils.securityConfig.securityConfigType]
	}

	def currentAuth = {
		render view: "/s2ui/securityInfo/currentAuth", model: [auth: SecurityContextHolder.context.authentication]
	}

	def usercache = {
		render view: "/s2ui/securityInfo/usercache", model: [cache: SpringSecurityUtils.securityConfig.cacheUsers ? userCache.cache : null]
	}

	def filterChain = {
		render view: "/s2ui/securityInfo/filterChain", model: [filterChainMap: springSecurityFilterChain.filterChainMap]
	}

	def logoutHandler = {
		render view: "/s2ui/securityInfo/logoutHandlers", model: [handlers: logoutHandlers]
	}

	def voters = {
		render view: "/s2ui/securityInfo/voters", model: [voters: accessDecisionManager.decisionVoters]
	}

	def providers = {
		render view: "/s2ui/securityInfo/providers", model: [providers: authenticationManager.providers]
	}

	def secureChannel = {
		def securityMetadataSource = channelProcessingFilter?.securityMetadataSource
		render securityMetadataSource.getClass().name
	}
}
