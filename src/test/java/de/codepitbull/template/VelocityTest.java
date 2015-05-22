package de.codepitbull.template;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by jmader on 22.05.15.
 */
public class VelocityTest {
    @Test
    public void testVelocity() throws Exception{
        int counter = 0;
        VelocityContext ctx = new VelocityContext();
        ctx.put("val1","value1");
        ctx.put("val2","value2");
        ctx.put("val3",counter);

        RuntimeServices runtimeServices = RuntimeSingleton.getRuntimeServices();
        StringReader reader = new StringReader("$val1 and $val2 and $val3");
        SimpleNode node = runtimeServices.parse(reader, "Template name");
        Template template = new Template();
        template.setRuntimeServices(runtimeServices);
        template.setData(node);
        template.initDocument();

        long start = System.currentTimeMillis();
        for (int count = 0; count < 10_000_000; count++) {
            counter ++;
            ctx.put("val3",counter);
            StringWriter sw = new StringWriter();
            template.merge(ctx, sw);
            if(sw.toString().length()<0)
                throw new RuntimeException();
        }
        long stop = System.currentTimeMillis();
        System.out.println(VelocityTest.class.getSimpleName()+" elapsed " + (stop - start));
    }
}
