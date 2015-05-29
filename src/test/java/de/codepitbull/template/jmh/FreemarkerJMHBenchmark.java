package de.codepitbull.template.jmh;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static freemarker.template.Configuration.VERSION_2_3_22;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1, jvmArgs = {"-server", "-Xms1024M", "-Xmx1024M"})
@Threads(1)
@Warmup(iterations = 5)
@Measurement(iterations = 20)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class FreemarkerJMHBenchmark {
    private Template t;
    private long counter = 0;

    @Setup
    public void init() throws Exception {
        String templateStr = "$val1 and $val2 and $val3";
        t = new Template("name", new StringReader(templateStr),
                new Configuration(VERSION_2_3_22));
    }

    @Benchmark
    public void split(Blackhole bh) throws Exception {
        Map<String, Object> values = new HashMap<>();
        values.put("val1", "value1");
        values.put("val2", "value2");
        values.put("val3", counter++);
        StringWriter sw = new StringWriter();
        t.process(values, sw);
        bh.consume(sw.toString());
    }
}