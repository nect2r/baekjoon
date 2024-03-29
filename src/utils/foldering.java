/*
 * 백준 단계별로 풀어보기에 맞춰 패키지별로 파일을 구분하고있는데 너무 귀찮아서 제작
 * 
 * etc
 * https://www.acmicpc.net/step 에서 보여주는 단계와 https://www.acmicpc.net/step/? 에서 보여지는 ?인자 url이 다름
 * 보여주는 단계를 기준으로 정리하도록 진행
 * 
 * 종속성
 * jsoup.*.jar https://jsoup.org/download
 */
package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class foldering {

    public static void main(String[] args) {
        List<String[]> baekjoonProblemList = getBaekjoonProblemList(STEP_URL, STEP_TABLE_SELECTOR, STEP_TD_SELECTOR, PROBLEM_URL, PROBLEM_TABLE_SELECTOR);
        List<Map<String,String>> localProblemList = getLocalProblemList();
        makeFolder(baekjoonProblemList, localProblemList);
    }

    public static void makeFolder(List<String[]> baekjoonProblemList, List<Map<String,String>> localProblemList) {
        try {

            //백준 step1번 1번문제가 없으면
            //로컬에서 찾음
            //문제가 다른step에서 중복되어서 낮은 step으로 모두 move하고 상위 문제에서 중복되면 copy함
            //백준 step이 로컬step보다 크면 move
            //백준 step이 로컬step보다 작으면 copy

            File file = new File("temp");
            String projectPath = file.getAbsoluteFile().toString();
            String srcPath = projectPath.substring(0, projectPath.indexOf(File.separator + "temp")) + File.separator + "src";
            

            int baekjoonStepCount = 1;
            for(String[] baekjoonStep : baekjoonProblemList) {
                for(String baekjoonProblem : baekjoonStep) {
                    File problemFile = new File(srcPath + File.separator + "step_" + baekjoonStepCount + File.separator + "problem_" + baekjoonProblem + ".java");
                    System.out.println("존재여부 : " + problemFile.exists() + " 경로 : " + problemFile.toPath());

                    if(problemFile.exists()) {
                        for(int i = 0; i < localProblemList.size(); i++) {
                            Map<String,String> localProblem = localProblemList.get(i);
                            String localProblemPath = localProblem.get("path");

                            if(problemFile.toString().equals(localProblemPath)) {
                                localProblem.put("use", "Y");
                                localProblemList.set(i, localProblem);
                                break;
                            }
                        }
                    } else {
                        for(int i = 0; i < localProblemList.size(); i++) {
                            Map<String,String> localProblem = localProblemList.get(i);
                            String localProblemPath = localProblem.get("path");
                            String localProblemUse = localProblem.get("use");

                            if(localProblemPath.toString().contains("problem_" + baekjoonProblem + ".java")) {
                                System.out.println("찾는 파일 : " + "problem_" + baekjoonProblem + ".java" + " 있는 경로 : " + localProblemPath);

                                File localFile = new File(localProblemPath);
                                File currentFile  = new File(localFile.getParent());

                                String currentPath = currentFile.getName().toString();

                                Path localStepDir = Paths.get(srcPath + File.separator + "step_" + baekjoonStepCount);

                                if(!Files.exists(localStepDir)) {
                                    Files.createDirectory(localStepDir);
                                }

                                //step_1 처럼 숫자인경우 else none_step 인 경우
                                if(currentPath.contains("step_")) {
                                    int localStep = Integer.parseInt(currentPath.substring(currentPath.indexOf("_") + 1, currentPath.length()));
                                    System.out.println(" localStep : " + localStep);

                                    Path oldfile = Paths.get(localProblemPath);
                                    Path newfile = problemFile.toPath();

                                    //로컬 스텝이 백준스텝보다 크면 move
                                    if(localStep > baekjoonStepCount || localStep < baekjoonStepCount && localProblemUse.equals("N")) {
                                        moveFile(oldfile, newfile);
                                        localProblem.put("use", "Y");
                                        localProblem.put("path", newfile.toString());
                                        localProblemList.set(i, localProblem);
                                    } else if(localStep < baekjoonStepCount && localProblemUse.equals("Y")) {
                                        copyFile(oldfile, newfile);
                                    } 
                                } else {
                                    //none_step 이므로 move
                                    Path oldfile = Paths.get(localProblemPath);
                                    Path newfile = problemFile.toPath();
                                    
                                    moveFile(oldfile, newfile);
                                    localProblem.put("use", "Y");
                                    localProblem.put("path", newfile.toString());
                                    localProblemList.set(i, localProblem);
                                }

                                break;
                            }
                        }
                    }
                }
                baekjoonStepCount++;
            }

            //정렬하고 남은 파일은 none_step으로 이동
            for(int i = 0; i < localProblemList.size(); i++) {
                Map<String,String> localProblem = localProblemList.get(i);
                String localProblemPath = localProblem.get("path");
                String localProblemUse = localProblem.get("use");

                if(localProblemUse.equals("N")) {
                    Path oldfile = Paths.get(localProblemPath);
                    Path newfile = Paths.get(srcPath + File.separator + "none_step" + File.separator + oldfile.getFileName());
                    
                    moveFile(oldfile, newfile);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String line = System.getProperty("line.separator");

    public static void copyFile(Path oldfile, Path newfile) throws Exception{
        Files.copy(oldfile, newfile);

        List<String> allLines = Files.readAllLines(newfile, StandardCharsets.UTF_8);

        String pakage = "package " + newfile.getParent().toFile().getName().toString() + ";";

        for(int i = 0; i < allLines.size(); i++) {
            String line = allLines.get(i);
            if(line.contains("package ")) {
                allLines.set(i, pakage);
                break;
            }
        }

        StringBuilder sb = new StringBuilder();

        for(String str : allLines) {
            sb.append(str + line);
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newfile.toFile().getPath()), StandardCharsets.UTF_8));
        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    public static void moveFile(Path oldfile, Path newfile) throws Exception{
        Files.move(oldfile, newfile);

        List<String> allLines = Files.readAllLines(newfile, StandardCharsets.UTF_8);

        String pakage = "package " + newfile.getParent().toFile().getName().toString() + ";";

        for(int i = 0; i < allLines.size(); i++) {
            String line = allLines.get(i);
            if(line.contains("package ")) {
                allLines.set(i, pakage);
                break;
            }
        }

        StringBuilder sb = new StringBuilder();

        for(String str : allLines) {
            sb.append(str + line);
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newfile.toFile().getPath()), StandardCharsets.UTF_8));
        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    public static List<Map<String,String>> getLocalProblemList() {
        List<Map<String,String>> problemList = new ArrayList<>();

        File file = new File("temp");
        String projectPath = file.getAbsoluteFile().toString();
        String srcPath = projectPath.substring(0, projectPath.indexOf(File.separator + "temp")) + File.separator + "src";

        try {
            Path problemPath = Paths.get(srcPath);
    
            List<Path> result;
            Stream<Path> walk = Files.walk(problemPath);

            result = walk.filter(Files::isRegularFile).collect(Collectors.toList());
            for(Path path : result) {
                //로컬 파일 none_step과 step_1만 포함
                if(path.toString().contains("none_step") || path.toString().contains("step_")) {
                    Map<String,String> map = new HashMap<>();

                    map.put("path", path.toString());
                    map.put("use", "N");
    
                    problemList.add(map);
                }
            }

            walk.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return problemList;
    }

    //단계확인경로
    static final String STEP_URL = "https://www.acmicpc.net/step";

    //단계확인 css selector
    static final String STEP_TABLE_SELECTOR = ".table.table-bordered.table-striped tbody tr";

    //단계 href css selector
    static final String STEP_TD_SELECTOR = "td:nth-child(2) a";

    //문제확인 기본경로
    static final String PROBLEM_URL = "https://www.acmicpc.net";

    //문제확인 css selector
    static final String PROBLEM_TABLE_SELECTOR = "#problemset tbody tr td.list_problem_id";

    /**
     * step별로 problem을 가져온다. List 순서가 step이며, String[]은 problem을 순서대로 있음
     * @param url
     * @param tableSelector
     * @param tdSelector
     * @param problemUrl
     * @param problemTableSelector
     * @return
     */
    public static List<String[]> getBaekjoonProblemList(String url, String tableSelector, String tdSelector, String problemUrl, String problemTableSelector) {
        List<String[]> resultList = new ArrayList<>();

        try{
            List<String> stepList = new ArrayList<>();

            Document doc = Jsoup.connect(url).get();

            Elements tr = doc.select(tableSelector);

            for(Element element : tr) {
                String href = element.select(tdSelector).attr("href");
                stepList.add(href);
            }

            for(int i = 0; i < stepList.size(); i++) {
                String href = stepList.get(i);
    
                Document problemDoc = Jsoup.connect(problemUrl + href).get();

                Elements problemTr = problemDoc.select(problemTableSelector);
                String[] problemList = new String[problemTr.size()];

                for(int j = 0; j < problemTr.size(); j++) {
                    Element element = problemTr.get(j);
                    String problem = element.text();
                    problemList[j] = problem;
                }

                resultList.add(problemList);
            }
        } catch(Exception e) {
           e.printStackTrace();
        }

        return resultList;
    }
}