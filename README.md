Event Notification SDK 
==========
With notifications, business moments are communicated to all interested listeners a.k.a. subscribers of those event streams. eBay's most recent notification payloads are also secured using ECC signature headers.  

This OpenAPI powered springboot SDK is designed to simplify processing eBay notifications. The application receives subscribed messages, validates the integrity of the message using the X-EBAY-SIGNATURE header and delegates to a custom configurable MessageProcessor for plugging in usecase specific processing logic. 

Table of contents
==========
* [What Notifications are covered?](#notifications)
* [Features](#features)
* [Usage](#usage)
* [Logging](#logging)
* [Future enhancements](#enhancements)
* [License](#license)


# Notifications

This SDK is intended for the latest eBay notifications that use ECC signatures and JSON payloads. 
While this SDK is generic for any topics, it currently includes the schema definition for MARKETPLACE_ACCOUNT_DELETION notifications. 

This SDK now also incorporates support for endpoint validation.

# Features

This SDK is intended to bootstrap subscriptions to eBay Notifications and provides a ready springboot deployable. 
This SDK incorporates

* A deployable application that is generic across topics and can process incoming https notifications
* Allows registration of custom Message Processors.  
* Declarative OpenAPI powered schema definitions and code generation
* [Verify the integrity](https://github.com/eBay/event-notification-java-sdk/blob/faba02735555631189e1dca5c771fabc9646ab66/src/main/java/com/ebay/commerce/notification/utils/SignatureValidator.java#L48) of the incoming messages 
    * Use key id from the decoded signature header to fetch public key required by the verification algorithm. An LRU cache is used to prevent refetches for same 'key'.
    * On verification success, delegate processing to the registered custom message processor and respond with a 204 HTTP status code.  
    * On verification failure, respond back with a 412 HTTP status code 
* With release 1.1.0 - includes support for [generating the challenge response](https://github.com/eBay/event-notification-java-sdk/blob/150d231d5601bc87d97ca143588b43ea6a0513f0/src/main/java/com/ebay/commerce/notification/utils/EndpointValidator.java#L37) required for validating this endpoint.    
More details on endpoint validation is documented [here](https://developer.ebay.com/marketplace-account-deletion).

# Usage

**Prerequisites**
```
maven: version 3.5.0 (or later)
jdk: 8 (or later)
```
**Configure**

Update [application.yaml](src/main/resources/application.yaml)  with: 
* Path to client credentials (required to fetch Public Key from /commerce/notification/v1/public_key/{public_key_id}).  
 Sample Client Credentials Configuration: [ebay-config.yaml](samples/ebay-config.yaml).

* Environment (PRODUCTION or SANDBOX). Default: PRODUCTION

* Verification token associated with your endpoint. A random sample is included for your endpoint.
  It is recommended that this verification token be stored in a secure location and this needs to be the same as that provided to eBay. 

* Endpoint specific to this deployment. A random url is included as an example. 

For MARKETPLACE_ACCOUNT_DELETION use case simply implement custom logic in [AccountDeletionMessageProcessor.processInternal()](https://github.com/eBay/event-notification-java-sdk/blob/main/src/main/java/com/ebay/commerce/notification/processor/AccountDeletionMessageProcessor.java) 


**Install and Run**
```
mvn spring-boot:run 

ngrok http --domain=enough-pup-safely.ngrok-free.app 7070
```

**Onboard any new topic in 3 simple steps! :**

* Add schema definition for new topic data to [data.yaml](src/main/resources/definitions/data.yaml) 
* Add a custom MessageProcessor for new topic that extends [BaseMessageProcessor](https://github.com/eBay/event-notification-java-sdk/blob/main/src/main/java/com/ebay/commerce/notification/processor/BaseMessageProcessor.java) abstract class and implement 'processInternal(T data)'
* Register the new MessageProcessor in [NotificationConfig.registerMessageProcessors()](https://github.com/eBay/event-notification-java-sdk/blob/faba02735555631189e1dca5c771fabc9646ab66/src/main/java/com/ebay/commerce/notification/config/EventNotificationConfig.java#L72)


Note on Production deploys
```
For production, please host with HTTPS enabled.
```

## Logging

Uses standard slf4j logging. 

## Enhancements

Once AsyncAPI opensource tooling supports an ability to limit code generation to models only from a subscriber facing contract, will switch the implementation to use AsyncAPI contracts exclusively. The [schema definitions are externalized](src/main/resources/definitions/data.yaml) so a transition to AsyncAPI codegen would be trivial.  

## License

Copyright 2021 eBay Inc.  
Developer: Sekhar Banerjee

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

## Restrictions
https://developer.ebay.com/api-docs/commerce/notification/overview.html#restrictions
