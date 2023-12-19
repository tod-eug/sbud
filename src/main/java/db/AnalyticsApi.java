package db;

import org.telegram.telegrambots.meta.api.objects.User;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class AnalyticsApi {

    public static void createEvent(User user, String chatId, String command, String text, String callback) {

        String userUuid = DbUsersApi.findUserByTgId(user.getId().toString(), user, chatId);

        SimpleDateFormat formatter = new SimpleDateFormat(DatabaseHelper.createDateDefaultPattern);
        String createdDate = formatter.format(new Date());


        UUID id = UUID.randomUUID();

        String insertQuery = String.format("insert into analytics (id, user_id, command, text, callback, create_date) VALUES ('%s', '%s', '%s', '%s', '%s', '%s');",
                id, userUuid, command, text, callback, createdDate);

        DatabaseHelper dbHelper = new DatabaseHelper();
        try {
            dbHelper.getPreparedStatement(insertQuery).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.closeConnections();
        }
    }
}
