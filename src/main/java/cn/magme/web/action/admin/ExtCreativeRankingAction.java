package cn.magme.web.action.admin;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.magme.pojo.FpageInfo;
import cn.magme.pojo.sns.CreativeRanking;
import cn.magme.service.sns.CreativeRankingService;
import cn.magme.util.ToJson;

/**
 * 
 * @author fredy
 * @since 2013-1-14
 */
public class ExtCreativeRankingAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9190933910025177310L;
	private Logger log = Logger.getLogger(this.getClass());
	@Resource
	private CreativeRankingService creativeRankingService;
	
	public void page(){
		log.info("searching page:" + page.getCurPage());
        page = this.creativeRankingService.getByPage(page);
        String info = ToJson.object2json(page);
        print(info);
	}
	
    public void delete() {
        log.info("delete ids:" + ids);
        String[] strArr = ids.split(",");
        this.creativeRankingService.delete(super.strArrToLongArr(strArr));
    }

	  public void commit() {
	        Object[] arr = super.toJavaArr(info, CreativeRanking.class);
	        CreativeRanking[] infos = castToCreativeRanking(arr);
	        this.creativeRankingService.commit(infos);
	   }
	  
	  private CreativeRanking[] castToCreativeRanking(Object[] arr) {
		  CreativeRanking[] infos = new CreativeRanking[arr.length];
	      for (int i = 0; i < infos.length; i++) {
	          infos[i] = (CreativeRanking) arr[i];
	      }
	      return infos;
	  }

}
