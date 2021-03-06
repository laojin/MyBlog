package cn.coselding.myblog.action;

import cn.coselding.myblog.domain.Article;
import cn.coselding.myblog.service.impl.ArticleServiceImpl;
import cn.coselding.myblog.service.impl.VisitorServiceImpl;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by 宇强 on 2016/3/13 0013.
 */
public class LikeAction extends ActionSupport{

    public static final String LIKE_TOKEN = "like";

    private int artid;

    public int getArtid() {
        return artid;
    }

    public void setArtid(int artid) {
        this.artid = artid;
    }

    @Override
    public String execute() throws Exception {

        //获取当前用户session
        HttpSession session = ServletActionContext.getRequest().getSession();
        //还没like过就能like
        if(session.getAttribute(LIKE_TOKEN+artid)==null) {
            //刷新数据库
            VisitorServiceImpl service = new VisitorServiceImpl();
            int likes = service.likeArticle(artid);

            //设置当前的用户session已经like过了
            session.setAttribute(LIKE_TOKEN+artid, "true");
        }

        ArticleServiceImpl service = new ArticleServiceImpl();
        Article article = service.queryArticle(artid);

        //获取请求前的页面
        PrintWriter out = ServletActionContext.getResponse().getWriter();
        out.println(article.getLooked()+";"+article.getLikes());
        out.close();
        return null;
    }

    /**获取文章的喜爱数和浏览数
     * @return
     * @throws Exception
     */
    public String getData() throws Exception {
        VisitorServiceImpl service = new VisitorServiceImpl();
        Article article = service.getData(artid);
        //回写响应数据
        PrintWriter out = ServletActionContext.getResponse().getWriter();
        out.write(article.getLooked()+";"+article.getLikes());
        out.close();
        return null;
    }

    /**获取首页文章列表的喜爱数和浏览数
     * @return
     * @throws Exception
     */
    public String getListData() throws Exception {
        VisitorServiceImpl service = new VisitorServiceImpl();
        Article article = service.getData(artid);
        //回写响应数据
        PrintWriter out = ServletActionContext.getResponse().getWriter();
        out.write(article.getLooked()+";"+article.getLikes());
        out.close();
        return null;
    }
}
