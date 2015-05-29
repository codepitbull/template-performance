package de.codepitbull.template.jmh;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.stringtemplate.v4.ST;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1, jvmArgs = {"-server", "-Xms1024M", "-Xmx1024M"})
@Threads(1)
@Warmup(iterations = 5)
@Measurement(iterations = 20)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class VelocityJMHBenchmark {
    private Template template;
    private VelocityContext ctx;
    private long counter = 0;

    @Setup
    public void init() throws Exception {
        ctx = new VelocityContext();
        ctx.put("val1","value1");
        ctx.put("val2","value2");
        ctx.put("val3",counter);

        RuntimeServices runtimeServices = RuntimeSingleton.getRuntimeServices();
        StringReader reader = new StringReader("$val1 and $val2 and $val3");
        SimpleNode node = runtimeServices.parse(reader, "Template name");
        template = new Template();
        template.setRuntimeServices(runtimeServices);
        template.setData(node);
        template.initDocument();
    }

    @Benchmark
    public void split(Blackhole bh) throws Exception {
        ctx.put("val3", counter++);
        StringWriter sw = new StringWriter();
        template.merge(ctx, sw);
        bh.consume(sw.toString());
    }
}