package threecirclesweb5



import grails.converters.JSON
import org.grails.datastore.mapping.validation.ValidationErrors
import org.springframework.dao.DataIntegrityViolationException
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass

class FriendController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }
	
    def list() {
      params.max = Math.min(params.max ? params.int('max') : 10, 100)
      render Friend.list(params) as JSON
    }

    def save() {
      def jsonObject = JSON.parse(params.friend)
      
      Friend friendInstance = new Friend(jsonObject)
      
      if (!friendInstance.save(flush: true)) {
        ValidationErrors validationErrors = friendInstance.errors
        render validationErrors as JSON
        return
      }
      
      def asJson = friendInstance as JSON
      event topic:"save-friend", data: asJson.toString()
      render friendInstance as JSON
    }
    
    def show() {
      def friendInstance = Friend.get(params.id)
      if (!friendInstance) {
        flash.message = message(code: 'default.not.found.message', args: [message(code: 'friend.label', default: 'Friend'), params.id])
        render flash as JSON
        return
      }
      
      render friendInstance as JSON
    }

    def update() {
      def jsonObject = JSON.parse(params.friend)

      def friendInstance = Friend.get(jsonObject.id)

      if (!friendInstance) {
        flash.message = message(code: 'default.not.found.message', args: [message(code: 'friend.label', default: 'Friend'), params.id])
        render flash as JSON
        return
      }

      if (jsonObject.version) {
        def version = jsonObject.version.toLong()
        if (friendInstance.version > version) {
          friendInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                                                           [message(code: 'friend.label', default: 'Friend')] as Object[],
                                                           "Another user has updated this Friend while you were editing")
          ValidationErrors validationErrors = friendInstance.errors
          render validationErrors as JSON
          return
        }
      }

      Friend friendReceived = new Friend(jsonObject)

      new DefaultGrailsDomainClass(Friend.class).persistentProperties.each() {
          if (it.oneToOne || it.embedded) {
            friendInstance[it.name] = it.type.get(jsonObject["${it.name}.id"])
          } else {
            friendInstance[it.name] = friendReceived[it.name]
          }
      }
      
      if (!friendInstance.save(flush: true)) {
        ValidationErrors validationErrors = friendInstance.errors
        render validationErrors as JSON
        return
      }
      
      def asJson = friendInstance as JSON
      event topic:"update-friend", data: asJson.toString()
      render friendInstance as JSON
    }

    def delete() {
      def friendInstance = Friend.get(params.id)
      
      if (!friendInstance) {
        flash.message = message(code: 'default.not.found.message', args: [message(code: 'friend.label', default: 'Friend'), params.id])
        render flash as JSON
        return
      }
      try {
        friendInstance.delete(flush: true)
      }
      catch (DataIntegrityViolationException e) {
        flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'friend.label', default: 'Friend'), params.id])
        render flash as JSON
        return
      }
      
      event topic:"delete-friend", data: friendInstance
      render friendInstance as JSON
    }
    
}
