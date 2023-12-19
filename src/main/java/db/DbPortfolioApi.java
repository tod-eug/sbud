package db;

import dto.Ticker;
import org.telegram.telegrambots.meta.api.objects.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DbPortfolioApi {

    public static String addStock(User user, String chatId, Ticker ticker) {

        String userUuid = DbUsersApi.findUserByTgId(user.getId().toString(), user, chatId);

        SimpleDateFormat formatter = new SimpleDateFormat(DatabaseHelper.createDateDefaultPattern);
        String createdDate = formatter.format(new Date());


        UUID id = UUID.randomUUID();

        String insertQuery = String.format("insert into portfolio (id, user_id, ticker, pretty_ticker, name, currency, exchange, exchange_full_name, create_date) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                id, userUuid, ticker.getTicker(),ticker.getPrettyTicker(), ticker.getName(), ticker.getCurrency(), ticker.getExchange(), ticker.getExchangeFullName(), createdDate);

        DatabaseHelper dbHelper = new DatabaseHelper();
        try {
            dbHelper.getPreparedStatement(insertQuery).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.closeConnections();
        }
        return id.toString();
    }

    public static List<Ticker> getPortfolio(User user, String chatId) {

        String userUuid = DbUsersApi.findUserByTgId(user.getId().toString(), user, chatId);

        String sql = String.format("select * from portfolio where user_id = '%s';", userUuid);

        DatabaseHelper dbHelper = new DatabaseHelper();
        List<Ticker> result = new ArrayList<>();
        try {
            ResultSet st = dbHelper.getPreparedStatement(sql).executeQuery();
            while(st.next()) {
                result.add(new Ticker(st.getString("ticker"), st.getString("pretty_ticker"), st.getString("name"),
                        st.getString("currency"), st.getString("exchange"), st.getString("exchange_full_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.closeConnections();
        }
        return result;
    }

    public static void deleteTicker(User user, String chatId, String ticker) {

        String userUuid = DbUsersApi.findUserByTgId(user.getId().toString(), user, chatId);

        String sql = String.format("delete from portfolio where user_id = '%s' and ticker = '%s';", userUuid, ticker);

        DatabaseHelper dbHelper = new DatabaseHelper();
        try {
            dbHelper.getPreparedStatement(sql).executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.closeConnections();
        }
    }
}
