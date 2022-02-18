package com.sluzbenik.SluzbenikApp.repository.rdfRepository;


import com.sluzbenik.SluzbenikApp.utils.AuthenticationUtilities;
import com.sluzbenik.SluzbenikApp.utils.SparqlUtil;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.sluzbenik.SluzbenikApp.repository.Constants.DZS_NAMED_GRAPH_URI;


@Component
public class AdvancedSearchRepository {

    protected AuthenticationUtilities.ConnectionPropertiesFusekiJena conn;

    public AdvancedSearchRepository(){
        conn = AuthenticationUtilities.setUpPropertiesFusekiJena();
    }

    enum ConverterEnum {DATE, LITERAL, RESOURCE};

    public String checkAndGetSignDate(String chars){
        //<, >, <=, >=, =, !=
        if (chars.charAt(0) == '>'){
            if (chars.charAt(1) == '='){
                return ">=";
            }else {
                return ">";
            }
        }else if (chars.charAt(0) == '<'){
            if (chars.charAt(1) == '='){
                return "<=";
            }else {
                return "<";
            }
        }else if (chars.charAt(0) == '='){
            return "=";
        }else if (chars.charAt(0) == '!'){
            if (chars.charAt(1) == '='){
                return "!=";
            }
        }
        throw new RuntimeException("Invalid sign");
    }

    public String checkAndGetSignEqNeq(String chars){
        //=, !=
        if (chars.charAt(0) == '='){
            return "=";
        }else if (chars.charAt(0) == '!'){
            if (chars.charAt(1) == '='){
                return "!=";
            }
        }
        throw new RuntimeException("Invalid sign");
    }

    public String expConverter(String expr, String predicateName, ConverterEnum type){ //type= date, literal, resource
        if (!expr.startsWith(predicateName))
            throw new RuntimeException(expr + " does not start with " + predicateName);

        if (type == ConverterEnum.DATE){
            String sign = checkAndGetSignDate(expr.substring(predicateName.length(), predicateName.length()+2));
            String exprRightSide = expr.substring(predicateName.length()+sign.length());

            String queryExp = "<http://www.w3.org/2001/XMLSchema#date>(?" + predicateName + ") ";
            queryExp += sign;
            queryExp += " <http://www.w3.org/2001/XMLSchema#date>(" + exprRightSide + ")";
            return queryExp;

        }else if (type == ConverterEnum.LITERAL){
            return "?" + expr;
        } else if (type == ConverterEnum.RESOURCE){
            String sign = checkAndGetSignEqNeq(expr.substring(predicateName.length(), predicateName.length()+2));
            String rightSide;
            String prefix;
            if (sign.equals("=")){
                prefix = "contains";
                rightSide = expr.split("=")[1];
            }else{
                prefix = "!contains";
                rightSide = expr.split("!=")[1];
            }
            return prefix + "(str(?" + predicateName + ")," + rightSide + ")";
        }
        throw new RuntimeException("Neka greska");
    }

    public String expConverterForDZS(String exp){ //jer imaju iste metapodatke
        if (exp.contains("createdAt")){
            return this.expConverter(exp, "createdAt", ConverterEnum.DATE);
        }else if (exp.contains("createdBy")){
            return this.expConverter(exp, "createdBy", ConverterEnum.RESOURCE);
        }
        else if (exp.contains("issuedTo")){
            return this.expConverter(exp, "issuedTo", ConverterEnum.RESOURCE);
        }else if (exp.contains("refBy")){
            return this.expConverter(exp, "refBy", ConverterEnum.RESOURCE);
        }
        return null;
    }

    public String formSaglasnostDZS(String queryToDB){
        String sparqlCondition = "SELECT distinct ?s "+
                        "WHERE " +
                        "{ ?s <http://www.vakc-sistem.rs/predicate/issuedTo> ?issuedTo; " +
                        "<http://www.vakc-sistem.rs/predicate/createdAt> ?createdAt;" +
                        "<http://www.vakc-sistem.rs/predicate/refBy> ?refBy;" +
                        "FILTER(" + queryToDB + ")}" +
                        "GROUP BY ?s";
        System.out.println(sparqlCondition);
        return SparqlUtil.selectData(conn.dataEndpoint +
                DZS_NAMED_GRAPH_URI, sparqlCondition);
    }

    public List<String> advancedSearch(String query){
        //createdAt, issuedTo, refBy
        //($createdAt='2022-01-09'$&&$issuedTo='213223122'$)||$refBy='djura'$
        //xsd:date(?createdAt)=xsd:date('2022-01-09') && contains(str(?issuedTo), '999090999999') && contains(str(?refBy), '999090999999')

        int expStart, expEnd = 0;
        StringBuilder sb = new StringBuilder(100);
        for (int i =0; i < query.length(); i++){
            if (query.charAt(i) == '(' || query.charAt(i) == ')' || query.charAt(i) == '&' || query.charAt(i) == '|'){
                sb.append(query.charAt(i));
            }else if (query.charAt(i) == '$'){
                if (i == query.length() - 1){
                    break;
                }
                int oldI = i;
                expStart = i + 1;
                expEnd = query.indexOf('$', i+1);
                i = expEnd;

                String exp = query.substring(expStart, expEnd);
                String preparedExp = expConverterForDZS(exp);
                if (preparedExp == null){
                    i = oldI;
                }else{
                    sb.append(preparedExp);
                }
            }
        }
        String queryToDB = sb.toString();
        System.out.println(queryToDB);

        String sparqlQuery = formSaglasnostDZS(queryToDB);

        QueryExecution queryToExecute = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

        ResultSet results = queryToExecute.execSelect();

        List<String> consentList = new ArrayList<>();
        while(results.hasNext()) {
            QuerySolution res = results.nextSolution();
            String consent = res.get("s").toString();
            consentList.add(consent); //uzimamo samo id
        }
        queryToExecute.close();
        return consentList;
    }
}
