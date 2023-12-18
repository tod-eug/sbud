package bot.MessageProviders;

import java.util.List;

public class PortfolioMP {

    public static String getShowPortfolioText(List<String> portfolio) {

        StringBuilder sb = new StringBuilder();

        sb.append("Users portfolio: \n");
        for (int i = 1; i < portfolio.size(); i++) {
            sb.append(i).append(". ").append(portfolio.get(i)).append("\n");
        }
        return sb.toString();
    }
}
