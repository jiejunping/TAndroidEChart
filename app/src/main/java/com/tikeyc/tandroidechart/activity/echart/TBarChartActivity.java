package com.tikeyc.tandroidechart.activity.echart;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;

import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Toolbox;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.axis.Axis;
import com.github.abel533.echarts.axis.AxisLabel;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.feature.DataView;
import com.github.abel533.echarts.feature.Feature;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.feature.Restore;
import com.github.abel533.echarts.feature.SaveAsImage;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Series;
import com.tikeyc.tandroidechart.R;
import com.tikeyc.tandroidechart.activity.base.TBaseActivity;
import com.tikeyc.tandroidechartlibrary.TEChartWebView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TBarChartActivity extends TBaseActivity {

    @ViewInject(R.id.barChartWebView)
    private TEChartWebView barChartWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.isLandScape = true;
        super.onCreate(savedInstanceState);
        //设置横屏
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        setContentView(R.layout.activity_tbar_chart);

        initData();
        initView();
    }



    private void initData() {

    }


    private void initView() {
        x.view().inject(this);

        //
        navigationBar_title_tv.setText("BarChart");
        //设置数据源
        barChartWebView.setDataSource(new TEChartWebView.DataSource() {
            @Override
            public GsonOption markLineChartOptions() {
                return getLineAndBarChartOption();
            }
        });
    }


    /**根据https://mvnrepository.com/artifact/com.github.abel533/ECharts
     * 结合http://echarts.baidu.com/examples.html官方实例
     * 配置json数据
     * @return
     */
    public GsonOption getLineAndBarChartOption() {
        //http://echarts.baidu.com/echarts2/doc/example/mix1.html
        GsonOption option = new GsonOption();
        //title
        String text = "text";
        String subText = "subText";
        option.title(text,subText);
        //tooltip
        Tooltip tooltip = new Tooltip();
        tooltip.trigger(Trigger.axis);
        option.tooltip(tooltip);
        //toolbox
        Toolbox toolbox = new Toolbox();
        toolbox.show(true);
        Map<String, Feature> feature = new HashMap<String, Feature>();
        feature.put("mark",new Feature().show(true));
        feature.put("dataView",new DataView().show(true).readOnly(false));
        feature.put("magicType",new MagicType(Magic.line, Magic.bar).show(true));
        feature.put("restore",new Restore().show(true));
        feature.put("saveAsImage",new SaveAsImage().show(false));
        toolbox.setFeature(feature);
        option.toolbox(toolbox);
        //calculable
        option.setCalculable(true);
        //legend
        String legend1 = "蒸发量";
        String legend2 = "降水量";
        String legend3 = "平均温度";
        Legend legend = new Legend();
        legend.data(legend1,legend2,legend3);
        option.legend(legend);
        //grid
//            Grid grid = new Grid();
//            grid.y2(80);
//            option.grid(grid);
        //xAxis
        List<Axis> xAxis = new ArrayList<Axis>();
        CategoryAxis categoryAxis = new CategoryAxis();
        {
            List xAxisValues = new ArrayList();
            for (int i = 1; i <= 12; i++) {
                xAxisValues.add(i + "月");
            }
            categoryAxis.setData(xAxisValues);
        }
        xAxis.add(categoryAxis);
        option.xAxis(xAxis);
        //yAxis
        List<Axis> yAxis = new ArrayList<Axis>();
        {
            ValueAxis valueAxis = new ValueAxis();
            valueAxis.name("水量");
            valueAxis.axisLabel(new AxisLabel().formatter("{value} ml"));
            yAxis.add(valueAxis);
        }
        {
            ValueAxis valueAxis = new ValueAxis();
            valueAxis.name("温度");
            valueAxis.axisLabel(new AxisLabel().formatter("{value} °C"));
            yAxis.add(valueAxis);
        }
        option.yAxis(yAxis);
        //series
        List<Series> series = new ArrayList<Series>();
        {
            Bar bar = new Bar();
            bar.name(legend1).type(SeriesType.bar).yAxisIndex(0);
            List data = new ArrayList();
            double arrays[] = {2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3};
            for (double value : arrays){
                data.add(value);
            }
            bar.setData(data);
            series.add(bar);
        }
        {
            Bar bar = new Bar();
            bar.name(legend2).type(SeriesType.bar).yAxisIndex(0);
            List data = new ArrayList();
            double arrays[] = {2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3};
            for (double value : arrays){
                data.add(value);
            }
            bar.setData(data);
            series.add(bar);
        }
        {
            Line line = new Line();
            line.name(legend3).type(SeriesType.line).yAxisIndex(1);
            List data = new ArrayList();
            double arrays[] = {2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2};
            for (double value : arrays){
                data.add(value);
            }
            line.setData(data);
            series.add(line);
        }
        option.series(series);
        //
        return option;
    }
}
