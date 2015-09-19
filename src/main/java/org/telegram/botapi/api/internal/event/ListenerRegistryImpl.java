package org.telegram.botapi.api.internal.event;

import org.telegram.botapi.api.event.Event;
import org.telegram.botapi.api.event.Listener;
import org.telegram.botapi.api.event.ListenerRegistry;
import org.telegram.botapi.api.event.chat.*;
import org.telegram.botapi.api.event.chat.message.*;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * @author DarkSeraphim.
 */
public class ListenerRegistryImpl implements ListenerRegistry {

	private final Map<Class<?>, BiConsumer<Listener, ? extends Event>> invokers = new HashMap<Class<?>, BiConsumer<Listener, ? extends Event>>() {{
		register(AudioMessageReceivedEvent.class, Listener::onAudioMessageReceived);
		register(CommandMessageReceivedEvent.class, Listener::onCommandMessageReceived);
		register(ContactMessageReceivedEvent.class, Listener::onContactMessageReceived);
		register(DocumentMessageReceivedEvent.class, Listener::onDocumentMessageReceived);
		register(LocationMessageReceivedEvent.class, Listener::onLocationMessageReceived);
		register(MessageReceivedEvent.class, Listener::onMessageReceived);
		register(PhotoMessageReceivedEvent.class, Listener::onPhotoMessageReceived);
		register(StickerMessageReceivedEvent.class, Listener::onStickerMessageReceived);
		register(TextMessageReceivedEvent.class, Listener::onTextMessageReceived);
		register(VideoMessageReceivedEvent.class, Listener::onVideoMessageReceived);
		register(VoiceMessageReceivedEvent.class, Listener::onVoiceMessageReceived);

		register(DeleteGroupChatPhotoEvent.class, Listener::onDeleteGroupChatPhoto);
		register(GroupChatCreatedEvent.class, Listener::onGroupChatCreated);
		register(NewGroupChatPhotoEvent.class, Listener::onNewGroupChatPhoto);
		register(NewGroupChatTitleEvent.class, Listener::onNewGroupChatTitle);
		register(ParticipantJoinGroupChatEvent.class, Listener::onParticipantJoinGroupChat);
		register(ParticipantLeaveGroupChatEvent.class, Listener::onParticipantLeaveGroupChat);
	}

		private <T extends Event> void register(Class<T> clazz, BiConsumer<Listener, T> invoker) {
			this.put(clazz, invoker);
		}
	};

	// LinkedHashSet for iteration speed
	private final Map<Class<?>, Set<Listener>> listenerByContent = new HashMap<>();

	public void register(Listener listener) {
		for (Method m : listener.getClass().getDeclaredMethods()) {
			Class<?>[] classes = m.getParameterTypes();
			if (classes.length == 1) {
				Set<Listener> listeners = listenerByContent.computeIfAbsent(classes[0], (c) -> new LinkedHashSet<>());
				listeners.add(listener);
			}
		}
	}

	public void callEvent(Event event) {
		BiConsumer<Listener, Event> invoker = (BiConsumer<Listener, Event>) this.invokers.get(event.getClass());
		listenerByContent.getOrDefault(event.getClass(), Collections.emptySet()).forEach(listener -> invoker.accept(listener, event));
	}

	private ListenerRegistryImpl() {
	}

	public static ListenerRegistry getNewInstance() {

		return new ListenerRegistryImpl();
	}
}