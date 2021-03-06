# Changelog
#### Version 0.4.1
* Bugfix: In version 0.4.0 sending any messages without specifying the parse mode would result in a NullPointerException, this has now been corrected.

#### Version 0.4.0
* Feature: Added ability to use markdown in messages as specified in the API here https://core.telegram.org/bots/api#using-markdown
  * You can do this for SendableTextMessage's by setting the parse mode to ParseMode.MARKDOWN.
  * This feature is currently only supported by a few clients such as the android client, desktop client does not have support just yet.

#### Version 0.3.3
* Bugfix: Change URL InputFiles download location to the system temporary file directory to fix issues with detecting jar folder when using the API.
  * Will make the temp folder configurable in future versions.

#### Version 0.3.2
* Bugfix: Force telegram to use the correct filename for files downloaded through this API.

#### Version 0.3.1
* Bugfix: Catch any errors that occur during events as to not break incoming updates.

#### Version 0.3.0
* Supported downloading files from URL for sending in messages.
  * Added InputFile(URL url) to support this.
  * Automatic cacheing of downloaded files is implemented.
#### Version 0.2.5
* Added in more error handling to stop Exceptions being thrown when sending Photo/Audio/Document/Sticker/Video/Voice message types and the sending fails for some reason.

#### Version 0.2.4
* Bugfix: Made user_id optional in Contact content type to fix an error when a user_id is not included in the reply due to the contact not being a Telegram contact.

#### Version 0.2.3
* Added error handling to message sending, the API will no longer throw JSON errors and will print out a nice description of the error.
* Added error printing to the Update Manager so that it doesn't just fail silently if the telegram API is erroring.
* Fixed error when TelegramBot.getChat(String chatID) was provided with a null or zero-length chatID.

#### Version 0.2.2
* Fixed ChannelChat#sendMessage() always returning null and not sending the message.

#### Version 0.2.1
* Made the from field in the Message type optional due to channels not providing that field.
  * Thanks for mentioning you were changing that in your changelog Telegram.

#### Version 0.2
* Implemented Channels (Telegram HTTP API Updated on 8/10/2015)
  * This required changing the Chat#getID() method to return a String rather than an int, this will break some bots if this method was used.

#### Version 0.1
* First Alpha Release
  * Includes all features excluding the webhook message request method
