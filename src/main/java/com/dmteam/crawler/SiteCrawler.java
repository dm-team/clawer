package com.dmteam.crawler;

/**
 * Created by xh on 2014/11/24.
 */
public class SiteCrawler {

//    private String domain;
//
//    private String rootUrl;
//
//    private String destFolder;
//
//    public static ExecutorService es = Executors.newFixedThreadPool(30);
//
//    public SiteCrawler(String rootUrl, String destFolder) {
//        this.rootUrl = rootUrl;
//
//        if (rootUrl.startsWith("http")) {
//            this.domain = rootUrl.substring(rootUrl.indexOf("//")+2);
//            this.domain = this.domain.substring(this.domain.indexOf("/"));
//        }
//        this.domain = rootUrl.substring(0, rootUrl.indexOf("/"));
//        this.destFolder = destFolder;
//    }
//
//    public void start() throws IOException {
//
//
//        String packagePage = new HttpPageFetcher().fetch(rootUrl);
//
//        List<String> urls = UrlExtractor.create("regex",
//                "<td width=\"100%\">&nbsp;</td></tr><tr valign=\"top\">\\s*" +
//                        "<td><a href=\"\\S*\">\\S*</a>").extractUrls(packagePage);
//
//        for (int i=0;i<urls.size();i++) {
//            String url = urls.get(i);
//
//            System.out.println(url);
//
//
//            if (!url.startsWith("/")) {
//                url = "/" + url;
//            }
//
//            PackageFetcher f = new PackageFetcher(domain, url, destFolder);
//
//            es.submit(f);
//        }
//    }
//
//    private static class PackageFetcher implements Runnable {
//
//        private String domain;
//
//        private String uri;
//
//        private String folder;
//
//        private String packagePath;
//
//        PackageFetcher(String domain, String uri, String parentFolder) {
//            this.domain = domain;
//            this.uri = uri;
//
//            packagePath = uri.substring("/docs/api/".length());
//            packagePath = packagePath.substring(0, packagePath.lastIndexOf("/"));
//
//            this.folder = parentFolder + "/" + packagePath;
//        }
//
//        public void run() {
//
//            try {
//
//                File file = new File(this.folder);
//                if (!file.exists()) {
//                    file.mkdirs();
//                }
//
//                String packagePage = new HttpPageFetcher().fetch(domain + uri);
//
//                List<String> urls = UrlExtractor.create("regex",
//                        "<td nowrap><a href=\"/\\S*.java\">code</a>").extractUrls(packagePage);
//
//
//                for (String url : urls) {
//                    System.out.println("url: "+ url + ", des folder:" + folder);
//
//                    es.submit(new FileFetcher(domain, url, folder));
//                }
//
//            } catch (IOException e) {
//                System.out.printf("package url fetch fail, url: " + uri +"." + e);
//            }
//
//        }
//    }
//
//    private static class FileFetcher implements Runnable {
//
//        private String domain;
//
//        private String uri;
//
//        private String folder;
//
//        private String fileName;
//
//        FileFetcher(String domain, String uri, String folder) {
//            this.domain = domain;
//            this.uri = uri;
//            this.folder = folder;
//
//            this.fileName = uri.substring(uri.lastIndexOf("/") + 1) ;
//        }
//
//        public void run() {
//
//            try {
//                String page = new HttpPageFetcher().fetch(domain + uri);
//
//                File destFile = new File(folder + "/" + fileName);
//
//                BufferedWriter bw = new BufferedWriter(new FileWriter(destFile));
//
//                bw.write(page);
//
//                bw.close();
//
//            } catch (IOException ioe) {
//                System.out.println("IOException url: " + uri + ", dest file: " + folder + "/" + fileName + "." + ioe);
//
//            }
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//
//
//
//        new SiteCrawler("www.docjar.com/projects/openjdk-7-code.html", "D:/___folder").start();
//    }
}
