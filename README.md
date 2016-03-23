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
