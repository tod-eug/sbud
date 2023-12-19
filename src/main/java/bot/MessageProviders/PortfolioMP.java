package bot.MessageProviders;

import dto.Ticker;

import java.util.List;

public class PortfolioMP {

    public static String getShowPortfolioText(List<Ticker> portfolio) {

        StringBuilder sb = new StringBuilder();

        sb.append("User's portfolio: \n");
        for (int i = 0; i < portfolio.size(); i++) {
            sb.append(i + 1).append(". ").append(portfolio.get(i).getPrettyTicker()).append("\n");
        }
        return sb.toString();
    }
}
