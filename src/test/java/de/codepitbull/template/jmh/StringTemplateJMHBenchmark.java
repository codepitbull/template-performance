package de.codepitbull.template.jmh;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.stringtemplate.v4.ST;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static freemarker.template.Configuration.VERSION_2_3_22;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1, jvmArgs = {"-server", "-Xms1024M", "-Xmx1024M"})
@Threads(1)
@Warmup(iterations = 5)
@Measurement(iterations = 20)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class StringTemplateJMHBenchmark {
    private ST hello;
    private long counter = 0;

    @Setup
    public void init() throws Exception {
        hello = new ST("<val1> and <val2> and <val3>");
        hello.add("val1", "value1");
        hello.add("val2", "value2");
        hello.add("val3", counter);
    }

    @Benchmark
    public void split(Blackhole bh) throws Exception {
        hello.remove("val3");
        hello.add("val3", counter++);
        bh.consume(hello.render());
    }
}