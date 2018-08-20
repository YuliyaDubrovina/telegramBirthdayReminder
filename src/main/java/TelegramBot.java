import org.glassfish.grizzly.streams.StreamWriter;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;


public class TelegramBot extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();                   // Получаем текст входящего сообщения
        String txt = message.getText();
        if (txt.equals("/start")) {
            File fl = new File("ChatID.txt");

            try {
                FileWriter writer = new FileWriter("recource/ChatID.txt", true);
                writer.append(message.getChatId().toString());
                writer.append('\r');
                writer.append('\n');
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            message.getChatId().toString();
            sendMsg(message, "Hello, world! This is simple bot!");
        }
    }

    public String getBotUsername() {
        return "BirthdayReminderAplana_bot";
    }

    public String getBotToken() {
        return "631529018:AAGgJSMwKaxQSgSNsNw1nHbVQbZG2kgwUDU";
    }

    public void sendMsg(Message msg, String txt) {
        SendMessage s = new SendMessage();
        s.setChatId(msg.getChatId());
        s.setText(txt);
        try {
            execute(s);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String chatId, String txt) {
        SendMessage s = new SendMessage();
        s.setChatId(chatId);
        s.setText(txt);
        try {
            execute(s);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            TelegramBot tgb = new TelegramBot();
            telegramBotsApi.registerBot(tgb);

            DatesAndExcel excel = new DatesAndExcel();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("recource/ChatID.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                excel.excelReader();
                for (String name : excel.getNamesList()) {
                    tgb.sendMsg(line, name);
                }
            }
            reader.close();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
