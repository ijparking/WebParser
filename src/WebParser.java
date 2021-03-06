import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebParser {
	public static void main(String args[]) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("http://sminfo.smba.go.kr/gc/ei/GEI001R0.do?mode=&kedcd=&checkView=&moveViewPage=&cmMenuId=441010100&selectMenuId=&parMenuId=&returnCmMenuId=&returnUrl=&clickcontrol=disable&iInd_cd=&chkindex=&iGB=1&locSrchCd=2&sidoCd=01&gugunCd=&dongCd=&sidoNm=%EC%84%9C%EC%9A%B8&gugunNm=&dongNm=&emplTypOcpt=&emplClsFrDt=&emplClsToDt=&clsFrDt=&clsToDt=&estbDt=&enpTypCoYn=&enpTypIndvYn=&enpScdNormalYn=&enpScdCloseYn=&enpScdQuitYn=&vetureCd=&infoUpdFrDt=&infoUpdToDt=&ovdHoldFrDt=&ovdHoldToDt=&lcMngNgtFrDt=&lcMngNgtToDt=&npYn=&hmpIsuYn=&sanReIsuYn=&medcIsuYn=&enp1000Yn=Y&grwYn=&commYn=&stabYn=&actvYn=&pdYn=&wlfrYn=&iqFlag=S&gubun=&fnOptFldYn=&fnOptDtlFldYn=&cmQueryOption=08&cmTotalRowCount=82&cmPageNo=1&cmSortField=&cmSortOption=0&tITLESortOption=2&bZNOSortOption=2&htmlvalue=%3Cli%3E%EC%A7%80%EC%97%AD%5B%EC%8B%9C%EB%8F%84%5D%28%EC%84%9C%EC%9A%B8%29%3C%2Fli%3E%3Cli%3E%EC%97%85%EC%A2%85%EB%AA%85%28%EB%8F%84%EB%A7%A4+%EB%B0%8F+%EC%86%8C%EB%A7%A4%EC%97%85%29%3C%2Fli%3E%3Cli%3E%EC%A2%85%EC%97%85%EC%9B%90%EC%88%98%285%7E9%EB%AA%85%29%3C%2Fli%3E%3Cli%3E%EC%83%81%EC%9C%84+%EA%B8%B0%EC%97%85%EC%A0%84%EC%B2%B4%28Y%29%3C%2Fli%3E&cmQueryOptionCombo=00&cmQuery=&ksic9BzcCdNm=%EB%8F%84%EB%A7%A4+%EB%B0%8F+%EC%86%8C%EB%A7%A4%EC%97%85&ksic9BzcCd=G&iSido=01%5E%EC%84%9C%EC%9A%B8&iGugun=&iDong=&ipoCd=&emCn=5%5E10&estbDtCd=&certYn=&asetTSum=&sam=&emplClsFrDtDash=&emplClsToDtDash=&emplTypOcptNm=&hmpFormCd=&emplNop=&minScrrCd=&ntfyScrrCd=&crrCd=&slryTyp=&slry=&wkForm=&wkPlnlAddrb=&apprChnl=&check1=on&cmRowCountPerPage=3");
		try {
			httpClient.execute(httpget, new BasicResponseHandler() {
				@Override
				public String handleResponse(HttpResponse response) throws HttpResponseException, IOException {
					String res = new String(super.handleResponse(response).getBytes("utf-8"));
					Document doc = Jsoup.parse(res);
					Elements table = doc.select("table.table tbody tr");
					
					SMBDetail smb = new SMBDetail();
					
					for (Element tbl : table) {
						for (Element row : tbl.select("tr")) {
				            Elements tds = row.select("td");
				            if (tds.size() > 6) {
				                //System.out.println("["+tds.get(0).child(0).attr("onclick").substring(23, 33)+"]\t["+tds.get(0).text() + "]\t[" + tds.get(6).text()+"]");
				                //System.out.println("["+tds.get(0).child(0).attr("onclick").substring(23, 33)+"]");
				                smb.getDetail(tds.get(0).child(0).attr("onclick").substring(23, 33));
				            }
				        }
				    }
					
					return res;
				}
			});
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
