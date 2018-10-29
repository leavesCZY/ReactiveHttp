package leavesc.hello.retrofit2_rxjava2.http.basis;

/**
 * 作者：leavesC
 * 时间：2018/10/27 21:10
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class BaseRepo<T> {

    protected T remoteDataSource;

    public BaseRepo(T remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

}