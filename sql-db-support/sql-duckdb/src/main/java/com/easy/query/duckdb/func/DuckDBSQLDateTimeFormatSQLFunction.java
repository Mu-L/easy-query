package com.easy.query.duckdb.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.impl.ColumnFuncValueExpressionImpl;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create time 2023/10/6 21:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class DuckDBSQLDateTimeFormatSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final String javaFormat;

    public DuckDBSQLDateTimeFormatSQLFunction(List<ColumnExpression> columnExpressions, String javaFormat) {
        this.columnExpressions = columnExpressions;
        this.javaFormat = javaFormat;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        return getSQLSegment();
    }

    @Override
    public int paramMarks() {
        return columnExpressions.size();
    }

    public String getSQLSegment() {
        if (this.javaFormat != null) {

            String format = this.javaFormat;
            switch (format) {
                case "yyyy-MM-dd HH:mm:ss":
                    return "TO_CHAR(({0})::TIMESTAMP,'YYYY-MM-DD HH24:MI:SS')";
                case "yyyy-MM-dd HH:mm":
                    return "TO_CHAR(({0})::TIMESTAMP,'YYYY-MM-DD HH24:MI')";
                case "yyyy-MM-dd HH":
                    return "TO_CHAR(({0})::TIMESTAMP,'YYYY-MM-DD HH24')";
                case "yyyy-MM-dd":
                    return "TO_CHAR(({0})::TIMESTAMP,'YYYY-MM-DD')";
                case "yyyy-MM":
                    return "TO_CHAR(({0})::TIMESTAMP,'YYYY-MM')";
                case "yyyyMMddHHmmss":
                    return "TO_CHAR(({0})::TIMESTAMP,'YYYYMMDDHH24MISS')";
                case "yyyyMMddHHmm":
                    return "TO_CHAR(({0})::TIMESTAMP,'YYYYMMDDHH24MI')";
                case "yyyyMMddHH":
                    return "TO_CHAR(({0})::TIMESTAMP,'YYYYMMDDHH24')";
                case "yyyyMMdd":
                    return "TO_CHAR(({0})::TIMESTAMP,'YYYYMMDD')";
                case "yyyyMM":
                    return "TO_CHAR(({0})::TIMESTAMP,'YYYYMM')";
                case "yyyy":
                    return "TO_CHAR(({0})::TIMESTAMP,'YYYY')";
                case "HH:mm:ss":
                    return "TO_CHAR(({0})::TIMESTAMP,'HH24:MI:SS')";
            }


            if(format.contains("'")){
                return formatSingleQuote(format);
            }
            return formatDefault(format);
        }
        return "TO_CHAR(({0})::TIMESTAMP,'YYYY-MM-DD HH24:MI:SS.US')";
    }

    private static final Pattern FORMAT_PATTERN1 = Pattern.compile("(yyyy|yy|MM|dd|HH|hh|mm|ss|[MdHhmsa]|(?:(?!yyyy|yy|MM|dd|HH|hh|mm|ss|[MdHhmsa]).)+)");
    private static final Pattern FORMAT_PATTERN2 = Pattern.compile("(yyyy|yy|MM|dd|HH|hh|mm|ss|tt)");

    protected String formatSingleQuote(String format) {
        Matcher matcher = FORMAT_PATTERN1.matcher(format);
//        StringBuffer result = new StringBuffer();
        int i=1;
        List<String> results = new ArrayList<>();
        while (matcher.find()) {
            String match = matcher.group(1);
            switch (match) {
                case "yyyy":
                    results.add("TO_CHAR(({0})::TIMESTAMP,'YYYY')");
//                    matcher.appendReplacement(result, "YYYY");
                    break;
                case "yy":
                    results.add("TO_CHAR(({0})::TIMESTAMP,'YY')");
//                    matcher.appendReplacement(result, "YY");
                    break;
                case "MM":
                    results.add("TO_CHAR(({0})::TIMESTAMP,'MM')");
//                    matcher.appendReplacement(result, "%_a1");
                    break;
                case "M":
                    results.add("LTRIM(TO_CHAR(({0})::TIMESTAMP,'MM'),'0')");
                    break;
                case "dd":
                    results.add("TO_CHAR(({0})::TIMESTAMP,'DD')");
//                    matcher.appendReplacement(result, "%_a2");
                    break;
                case "d":
                    results.add("(CASE WHEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'DD'),1,1) = '0' THEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'DD'),2,1) ELSE TO_CHAR(({0})::TIMESTAMP,'DD') END)");
                    break;
                case "HH":
                    results.add("TO_CHAR({0}::TIMESTAMP,'HH24')");
//                    matcher.appendReplacement(result, "%_a3");
                    break;
                case "H":
                    results.add("(CASE WHEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'HH24'),1,1) = '0' THEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'HH24'),2,1) ELSE TO_CHAR(({0})::TIMESTAMP,'HH24') END)");
                    break;
                case "hh":
                    results.add("TO_CHAR({0}::TIMESTAMP,'HH12')");
                    break;
                case "h":
                    results.add("(CASE WHEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'HH12'),1,1) = '0' THEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'HH12'),2,1) ELSE TO_CHAR(({0})::TIMESTAMP,'HH12') END)");
                    break;
                case "mm":
                    results.add("TO_CHAR({0}::TIMESTAMP,'MI')");
                    break;
                case "m":
                    results.add("(CASE WHEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'MI'),1,1) = '0' THEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'MI'),2,1) ELSE TO_CHAR(({0})::TIMESTAMP,'MI') END)");
                    break;
                case "ss":
                    results.add("TO_CHAR({0}::TIMESTAMP,'SS')");
                    break;
                case "s":
                    results.add("(CASE WHEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'SS'),1,1) = '0' THEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'SS'),2,1) ELSE TO_CHAR(({0})::TIMESTAMP,'SS') END)");
                    break;
                case "tt":
                    results.add("TO_CHAR({0}::TIMESTAMP,'AM')");
                    break;
                case "t":
                    results.add("RTRIM(TO_CHAR(({0})::TIMESTAMP,'AM'),'M')");
                    break;
                default:
                    columnExpressions.add(new ColumnFuncValueExpressionImpl(match));
                    results.add("{" + i++ + "}");
                    break;
            }
        }
        return "CONCAT(" + String.join(", ", results) + ")";
    }


    protected String formatDefault(String format1) {
        Matcher matcher = FORMAT_PATTERN2.matcher(format1);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String match = matcher.group(1);
            switch (match) {
                case "yyyy":
                    matcher.appendReplacement(result, "YYYY");
                    break;
                case "yy":
                    matcher.appendReplacement(result, "YY");
                    break;
                case "MM":
                    matcher.appendReplacement(result, "%_a1");
                    break;
                case "dd":
                    matcher.appendReplacement(result, "%_a2");
                    break;
                case "HH":
                    matcher.appendReplacement(result, "%_a3");
                    break;
                case "mm":
                    matcher.appendReplacement(result, "%_a4");
                    break;
                case "ss":
                    matcher.appendReplacement(result, "SS");
                    break;
                case "tt":
                    matcher.appendReplacement(result, "%_a5");
                    break;
            }
        }

        matcher.appendTail(result);
        String format= result.toString();

        String[] argsFinds = {"YYYY", "YY", "%_a1", "%_a2", "%_a3", "%_a4", "SS", "%_a5"};
        String[] argsSpts = format.split("(M|d|H|hh|h|m|s|t)");

        for (int a = 0; a < argsSpts.length; a++) {
            switch (argsSpts[a]) {
                case "M":
                    argsSpts[a] = "LTRIM(TO_CHAR(({0})::TIMESTAMP,'MM'),'0')";
                    break;
                case "d":
                    argsSpts[a] = "CASE WHEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'DD'),1,1) = '0' THEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'DD'),2,1) ELSE TO_CHAR(({0})::TIMESTAMP,'DD') END";
                    break;
                case "H":
                    argsSpts[a] = "CASE WHEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'HH24'),1,1) = '0' THEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'HH24'),2,1) ELSE TO_CHAR(({0})::TIMESTAMP,'HH24') END";
                    break;
                case "hh":
                    argsSpts[a] = "CASE mod(cast(CASE WHEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'HH24'),1,1) = '0' THEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'HH24'),2,1) ELSE TO_CHAR(({0})::TIMESTAMP,'HH24') END as number),12) " +
                            "WHEN 0 THEN '12' WHEN 1 THEN '01' WHEN 2 THEN '02' WHEN 3 THEN '03' WHEN 4 THEN '04' WHEN 5 THEN '05' WHEN 6 THEN '06' " +
                            "WHEN 7 THEN '07' WHEN 8 THEN '08' WHEN 9 THEN '09' WHEN 10 THEN '10' WHEN 11 THEN '11' END";
                    break;
                case "h":
                    argsSpts[a] = "CASE mod(cast(CASE WHEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'HH24'),1,1) = '0' THEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'HH24'),2,1) ELSE TO_CHAR(({0})::TIMESTAMP,'HH24') END as number),12) " +
                            "WHEN 0 THEN '12' WHEN 1 THEN '1' WHEN 2 THEN '2' WHEN 3 THEN '3' WHEN 4 THEN '4' WHEN 5 THEN '5' WHEN 6 THEN '6' " +
                            "WHEN 7 THEN '7' WHEN 8 THEN '8' WHEN 9 THEN '9' WHEN 10 THEN '10' WHEN 11 THEN '11' END";
                    break;
                case "m":
                    argsSpts[a] = "CASE WHEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'MI'),1,1) = '0' THEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'MI'),2,1) ELSE TO_CHAR(({0})::TIMESTAMP,'MI') END";
                    break;
                case "s":
                    argsSpts[a] = "CASE WHEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'SS'),1,1) = '0' THEN SUBSTR(TO_CHAR(({0})::TIMESTAMP,'SS'),2,1) ELSE TO_CHAR(({0})::TIMESTAMP,'SS') END";
                    break;
                case "t":
                    argsSpts[a] = "rtrim(TO_CHAR(({0})::TIMESTAMP,'AM'),'M')";
                    break;
                default:
                    String argsSptsA = argsSpts[a];
                    if (argsSptsA.startsWith("'")) {
                        argsSptsA = argsSptsA.substring(1);
                    }
                    if (argsSptsA.endsWith("'")) {
                        argsSptsA = argsSptsA.substring(0, argsSptsA.length() - 1);
                    }
                    if (Arrays.stream(argsFinds).anyMatch(argsSptsA::contains)) {
                        argsSpts[a] = "TO_CHAR(({0})::TIMESTAMP,'" + argsSptsA + "')";
                    } else {
                        argsSpts[a] = "'" + argsSptsA + "'";
                    }
                    break;
            }
        }

        if (argsSpts.length == 1) {
            format = argsSpts[0];
        }else if (argsSpts.length > 1) {
            format = "(" + String.join(" || ", Arrays.stream(argsSpts).filter(a -> !a.equals("''")).toArray(String[]::new)) + ")";
        }

        return format.replace("%_a1", "MM").replace("%_a2", "DD").replace("%_a3", "HH24").replace("%_a4", "MI").replace("%_a5", "AM");

    }

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return columnExpressions;
    }
}
