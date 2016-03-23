# EasyRecyclerView
####EasyRecyclerView 支持添加header、footer,设置空白页面(无数据,网络异常显示)。
####上拉加载更多,加载状态,网络异常状态,数据到底状态
####分页加载的全部解决方案，可以直接应用到项目中
===========================
<img src='https://github.com/hellosliu/EasyRecyclerView/blob/master/images/header_footer.gif' height='400'/> 
<img src='https://github.com/hellosliu/EasyRecyclerView/blob/master/images/loading.gif' height='400'/>
<img src='https://github.com/hellosliu/EasyRecyclerView/blob/master/images/loading_grid.gif' height='400'/>

### Gradle
```groovy
compile 'com.hellosliu.easyrecyclerview:easyrecyclerview:1.3.3'
```
### Usage
####添加header、footer,使用EasyRecylerView
```java
View header = layoutInflater.inflate(R.layout.view_customer_header, null);
View footer = layoutInflater.inflate(R.layout.view_customer_footer, null);
EasyRecylerView recylerView = (EasyRecylerView)findViewById(R.id.recycleview_header_footer);
recylerView.setLayoutManager(new LinearLayoutManager(this));

recylerView.addHeaderView(header); //添加header
recylerView.addFootView(footer);  //添加footer
recylerView.setAdapter(adapter);

recylerView.showEmptyView(emptyView); //显示无数据view
recylerView.showEmptyView(networkErrorView); //显示网络异常view

```
####上拉加载更多,分页加载,使用LoadMoreRecylerView
```java
View header = layoutInflater.inflate(R.layout.view_customer_header, null);
LoadMoreRecylerView recylerView = (LoadMoreRecylerView)findViewById(R.id.recycleview_loading);
recylerView.setLayoutManager(new LinearLayoutManager(this));
recylerView.addHeaderView(header);
//设置加载更多监听
recylerView.setOnRefreshListener(new OnRefreshListener() {
  @Override
  public void onRefresh() { //上拉loading
    getMoreData();
  }
  @Override
  public void onReload() {  //网络异常时,点击重新获取数据
    getMoreData();
  }
});
recylerView.setAdapter(adapter);
```

####更多操作
设置加载中、网络异常、数据到底文字
```java
recylerView.setSampleLoadText("Loading...", "NetWork Error", "Data End");
```
自定义加载中、网络异常、数据到底显示view 
```java
recylerView.setCustomerLoadFooter(loadingView, networkErrorView, dataEndView);
```

###EasyRecyclerView觉得好用，请点上面星星


