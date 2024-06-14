package bot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        try {
            bot bot = new bot();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "luninfirst_bot";
    }

    @Override
    public String getBotToken() {
        return "7415127840:AAFeSERSjlx0rf_JlwitJUxjuKcLezR6p5g";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String messageText = update.getMessage().getText(); // Получаем текст сообщения пользователя
            Long chatId = update.getMessage().getChatId(); // Получаем chatId пользователя
            switch (messageText) {
                case "/start" ->
                        sendMessage(chatId, "Привет, я Telegram bot, который поможет тебе узнать тепературу воздуха, давление и влажность в любом городе на данный момент времени).");
                case "/help" -> {
                    sendMessage(chatId, "Чтобы узнать погоду, отправь название города. Если такого города нет, я промолчу!");

                break;
                }
                default ->
                        sendMessage(chatId, Weather.getWeather(messageText));
            }
        }
    }

    // Метод для отправки сообщения пользователю
    public void sendMessage(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(messageText);
        sendMessage.setChatId(chatId);
        try {
            executeAsync(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

