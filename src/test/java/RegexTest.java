import java.util.regex.Pattern;

/**
 * Created by Administrator on 2014/11/28.
 */
public class RegexTest {
//                                                                    163.com_14_1126_06_ABV5ENLV00014AED+.html
//    private static Pattern fileNamePattern = Pattern.compile("^(.*).163.com_\\d{2}_\\d{4}_\\d{2}_[A-Z0-9]+(_\\d{1,2})?.html$");//
//                                                          politics.people.com.cn_n_2014_1215_c1024-26205370.html
    private static Pattern fileNamePattern = Pattern.compile(("^(.*).people.com.cn_n_\\d{4}_\\d{4}(_c\\d+-\\d{8})?.html.txt$"));
    public static void main(String[] args)

    {

      /*  String[] s = new String[] {
                "news.163.com_14_1126_06_ABV5ENLV00014AED+.html.txt",//
                "news.163.com_14_1126_06_ABV5ENLV00014AED_1.html",//
                "news.163.com_14_1126_06_ABV5ENLV00014AED_12.html",//
                "news.163.com_14_1126_06_ABV5ENLV00014AED_123.html",//
                "jj.news.163.com_14_1126_06_ABV5ENLV00014AED.html"
        };
       */


        String[] s = new String[]{
                "politics.people.com.cn_n_2014_1215_c1024-26205370.html.txt",
                "politics.people.com.cn_n_2014_1214_c1001-26205018.html",
                "tc.people.com.cn_n_2014_1215_c183175-26207730.html",
                "politics.people.com.cn_n_2014_1215_c1024-26205370.html"


        };
        for (String ss : s) {
            System.out.println(fileNamePattern.matcher(ss).matches());
        }
    }
}
