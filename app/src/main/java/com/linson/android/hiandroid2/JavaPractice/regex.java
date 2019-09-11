package com.linson.android.hiandroid2.JavaPractice;

import android.util.Log;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regex
{
    public boolean isEmail(String email)
    {
        boolean res=false;
        String regex_email="[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
        Pattern pattern_email=Pattern.compile(regex_email);
        Matcher matcher=pattern_email.matcher(email);
        return matcher.matches();
    }


    //有一个概念就是java中的匹配是用find()，逐步从前往后匹配的。
    //group(x)的中索引是匹配中的小分组。这里的表达式只有一个分组。没有多小分组。
    public List<String> findemails(String content)
    {
        List<String> res=new ArrayList<>();

        String regex_email="[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
        Pattern pattern_email=Pattern.compile(regex_email);
        Matcher matcher=pattern_email.matcher(content);
        int size=matcher.groupCount();
        LSComponentsHelper.LS_Log.Log_INFO(size+".size");
        while (matcher.find())
        {
            res.add(matcher.group());
        }
        return res;
    }

    //java 用函数matches和find来分别表达是否匹配和是否存在2个功能。（find可以多次调用来往后匹配）
    //
    public void startStudyRegex()
    {
        setpStudy();
        //testRegex();
    }

    private void setpStudy()
    {
        //先来一些简单的函数，如某个单词是否存在其中。 以xxx开头单词是否在其中，以某某某结尾的单词是否在其中。来直观感受某语言对REGEX的支持。
        //再开始学习每个语法符号的用处。
        //完毕后要完成，是否是电话号码，是否是小数，是否是邮件地址。那么就掌握了基础。
        //思考一个表达式语法如何可以表示任何东西?首先必须有语法表示：
        //某子段表达式出现0到无数次。只有掌握重复自己的技能，才有从有限到无限的可能。
        //所以需要有1，表示子段的能力。2表示重复次数的能力。
        //其次必须有1.表示某个符号的能力，2.表示任意符号的能力，3.表示某组符号中的一个的能力。那么这样的话，和前面的能力结合，理论上就可以表示任何东西。
        //当然扩展开来，有一个非符号，在某些情况下，可以表达更简洁,特别注意非，首先是某个可以表示确定含义的语法（确定的长度和符号范围）才能去非。[]是确定的语法，确定了1长度。任意字符为范围。
        //而对于匹配后的继续子匹配，可以在一次匹配中就表达出。需要扩展语法。java 用().
        //其实 regex 晦涩就在于细节多，碎片学习的话，可能就会对于()的分组作用漏学。导致误认为是选择含义，产生各种疑惑。因为各种组合表示可以有多种语法表示，有的简洁，有的复杂。所以加大了看懂别人表达式的难度。
        //[]是单个字符含义，()是选择和分组。{} 是表示重复概念。

        //LSComponentsHelper.LS_Log.Log_INFO(isA("appaaa", "app[a-z]*")+"");
        //LSComponentsHelper.LS_Log.Log_INFO(isA("abcappp", "[a-z]*app")+"");
        //LSComponentsHelper.LS_Log.Log_INFO(isExist("abcappleabc", "apple")+"");

        //\ 转义字符:没啥好说
        //LSComponentsHelper.LS_Log.Log_INFO(isExist("\r", "\r")+"");

        //^bucket$  ^ 明确了开头和结尾。


        //. 表示单个任意符号,除掉\r\n 。
        LSComponentsHelper.LS_Log.Log_INFO(isExist("hello", "h.llo")+"");

        //[]表示集合中的单个字符。
        LSComponentsHelper.LS_Log.Log_INFO(isExist("hello", "h[abce]llo")+"");

        //(|)表示集合中的任意一个项（可以多个字符组成一个项）  ,基本可以看成[xyz]的多字符 功能对应版.
        LSComponentsHelper.LS_Log.Log_INFO(isExist("hello", "h(a|ell|b)o")+"");

        //{n,m} 从n到m。              首先要记住这个最基本的
        //*表示左边元素重复 0或者多次
        //+ 1或者多次
        //? 0或者1  ,在重复语法符号后面。表示非贪婪模式。
        //{n} n次

        //999|99|9999
        LSComponentsHelper.LS_Log.Log_INFO(isExist("123|56|2345", "[1-9]{3}|[1-9]{2}|[1-9]{4}")+"");

        //^符号可以和[]结合  .注意[^x] ，首先[] 是表示需要一个符号，[^x]表示的确切意思是，表示一个非x的符号。
        //注意首先表示的是单个符号，非x是一个修饰。没有符号，或者2个非x符号。都不是所匹配的。
        LSComponentsHelper.LS_Log.Log_INFO("res:"+isA("abbz", "a[^c]z"));

        //^ 所有的匹配中，只有匹配的字符床处于最开头才是匹配的。 $所有的匹配中，只有匹配的字符床处于最结尾。 所以2个同时用。意思就是整个字符串，要刚刚好吻合表达式。而不是包含的意思。

        //()除了选择，还有分组功能。
        LSComponentsHelper.LS_Log.Log_INFO("res:"+isA("abcabcabc123", "(abc){3}[1-9]{3}"));

        //还有一些高级功能。Windows(?=95|98|NT|2000) .搜索的目标需要后面一些的限定。
        //这里要注意的是它不是找windows95.而是找windows. 但是又需要一些限制。
        //?=  ?!  ?<=  ?<!  这些符号的中文翻译也是死一堆人。 个人翻译：后面需要有；后面不能是，前面需要有，前面不能是。说人话会死吗？
        LSComponentsHelper.LS_Log.Log_INFO("res:"+isA("sdfjawindowklsdfj", "((?!window).)*"));


        // 所以我们看下^$配合上否定，才能完成的含义 ^((?!(art|Main)).)*$
        //^((?!(art|Main)).)*$  。如果是不使用 ^$ ,那么会有很多匹配的符号。因为有很多子字符串都不包含art啊，只有加上这个才是说 整句都不包含某个字符。而不是其中某段。
    }

    private void testRegex()
    {
        //掌握测试，电话号码，小数，数字，
        String mobiltel="1[0-9]{10}";
        String decimal="(0|[1-9]+[0-9]*).[0-9]*";
        LSComponentsHelper.LS_Log.Log_INFO("res:"+isA("36587412587", mobiltel));
        LSComponentsHelper.LS_Log.Log_INFO("res:"+isA("12574587545", mobiltel));
        LSComponentsHelper.LS_Log.Log_INFO("res:"+isA("1257458754512", mobiltel));
        LSComponentsHelper.LS_Log.Log_INFO("res:"+isA("125a4587545", mobiltel));
    }


    public boolean isA(String str,String regex)
    {
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(str);

        return matcher.matches();
    }

    public boolean isExist(String str,String regex)
    {
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(str);

        return matcher.find();
    }

    //开始分析email。首先需要1或多个字母。 接着0或多个任意符号，接着特定符号@ 最后1或多个任意符号（好像不能有特殊符号）

//
//    元字符
//
//            描述
//
//\
//
//    将下一个字符标记符、或一个向后引用、或一个八进制转义符。例如，“\\n”匹配\n。“\n”匹配换行符。序列“\\”匹配“\”而“\(”则匹配“(”。即相当于多种编程语言中都有的“转义字符”的概念。
//
//        ^
//
//    匹配输入字符串的开始位置。如果设置了RegExp对象的Multiline属性，^也匹配“\n”或“\r”之后的位置。
//
//    $
//
//    匹配输入字符串的结束位置。如果设置了RegExp对象的Multiline属性，$也匹配“\n”或“\r”之前的位置。
//
//        *
//
//    匹配前面的子表达式任意次。例如，zo*能匹配“z”，“zo”以及“zoo”。*等价于{0,}。
//
//        +
//
//    匹配前面的子表达式一次或多次(大于等于1次）。例如，“zo+”能匹配“zo”以及“zoo”，但不能匹配“z”。+等价于{1,}。
//
//        ?
//
//    匹配前面的子表达式零次或一次。例如，“do(es)?”可以匹配“do”或“does”中的“do”。?等价于{0,1}。
//
//    {n}
//
//    n是一个非负整数。匹配确定的n次。例如，“o{2}”不能匹配“Bob”中的“o”，但是能匹配“food”中的两个o。
//
//    {n,}
//
//    n是一个非负整数。至少匹配n次。例如，“o{2,}”不能匹配“Bob”中的“o”，但能匹配“foooood”中的所有o。“o{1,}”等价于“o+”。“o{0,}”则等价于“o*”。
//
//    {n,m}
//
//    m和n均为非负整数，其中n<=m。最少匹配n次且最多匹配m次。例如，“o{1,3}”将匹配“fooooood”中的前三个o。“o{0,1}”等价于“o?”。请注意在逗号和两个数之间不能有空格。
//
//        ?
//
//    当该字符紧跟在任何一个其他限制符（*,+,?，{n}，{n,}，{n,m}）后面时，匹配模式是非贪婪的。非贪婪模式尽可能少的匹配所搜索的字符串，而默认的贪婪模式则尽可能多的匹配所搜索的字符串。例如，对于字符串“oooo”，“o+?”将匹配单个“o”，而“o+”将匹配所有“o”。
//
//        .点
//
//    匹配除“\r\n”之外的任何单个字符。要匹配包括“\r\n”在内的任何字符，请使用像“[\s\S]”的模式。
//
//        (pattern)
//
//    匹配pattern并获取这一匹配。所获取的匹配可以从产生的Matches集合得到，在VBScript中使用SubMatches集合，在JScript中则使用$0…$9属性。要匹配圆括号字符，请使用“\(”或“\)”。
//
//        (?:pattern)
//
//    匹配pattern但不获取匹配结果，也就是说这是一个非获取匹配，不进行存储供以后使用。这在使用或字符“(|)”来组合一个模式的各个部分是很有用。例如“industr(?:y|ies)”就是一个比“industry|industries”更简略的表达式。
//
//        (?=pattern)
//
//    正向肯定预查，在任何匹配pattern的字符串开始处匹配查找字符串。这是一个非获取匹配，也就是说，该匹配不需要获取供以后使用。例如，“Windows(?=95|98|NT|2000)”能匹配“Windows2000”中的“Windows”，但不能匹配“Windows3.1”中的“Windows”。预查不消耗字符，也就是说，在一个匹配发生后，在最后一次匹配之后立即开始下一次匹配的搜索，而不是从包含预查的字符之后开始。
//
//        (?!pattern)
//
//    正向否定预查，在任何不匹配pattern的字符串开始处匹配查找字符串。这是一个非获取匹配，也就是说，该匹配不需要获取供以后使用。例如“Windows(?!95|98|NT|2000)”能匹配“Windows3.1”中的“Windows”，但不能匹配“Windows2000”中的“Windows”。
//
//        (?<=pattern)
//
//    反向肯定预查，与正向肯定预查类似，只是方向相反。例如，“(?<=95|98|NT|2000)Windows”能匹配“2000Windows”中的“Windows”，但不能匹配“3.1Windows”中的“Windows”。
//
//        (?<!pattern)
//
//    反向否定预查，与正向否定预查类似，只是方向相反。例如“(?<!95|98|NT|2000)Windows”能匹配“3.1Windows”中的“Windows”，但不能匹配“2000Windows”中的“Windows”。
//
//    x|y
//
//    匹配x或y。例如，“z|food”能匹配“z”或“food”或"zood"(此处请谨慎)。“(z|f)ood”则匹配“zood”或“food”。
//
//        [xyz]
//
//    字符集合。匹配所包含的任意一个字符。例如，“[abc]”可以匹配“plain”中的“a”。
//
//        [^xyz]
//
//    负值字符集合。匹配未包含的任意字符。例如，“[^abc]”可以匹配“plain”中的“plin”。
//
//        [a-z]
//
//    字符范围。匹配指定范围内的任意字符。例如，“[a-z]”可以匹配“a”到“z”范围内的任意小写字母字符。
//
//    注意:只有连字符在字符组内部时,并且出现在两个字符之间时,才能表示字符的范围; 如果出字符组的开头,则只能表示连字符本身.
//
//[^a-z]
//
//    负值字符范围。匹配任何不在指定范围内的任意字符。例如，“[^a-z]”可以匹配任何不在“a”到“z”范围内的任意字符。
//
//        \b
//
//    匹配一个单词边界，也就是指单词和空格间的位置（即正则表达式的“匹配”有两种概念，一种是匹配字符，一种是匹配位置，这里的\b就是匹配位置的）。例如，“er\b”可以匹配“never”中的“er”，但不能匹配“verb”中的“er”。
//
//        \B
//
//    匹配非单词边界。“er\B”能匹配“verb”中的“er”，但不能匹配“never”中的“er”。
//
//        \cx
//
//    匹配由x指明的控制字符。例如，\cM匹配一个Control-M或回车符。x的值必须为A-Z或a-z之一。否则，将c视为一个原义的“c”字符。
//
//        \d
//
//    匹配一个数字字符。等价于[0-9]。
//
//        \D
//
//    匹配一个非数字字符。等价于[^0-9]。
//
//        \f
//
//    匹配一个换页符。等价于\x0c和\cL。
//
//        \n
//
//    匹配一个换行符。等价于\x0a和\cJ。
//
//        \r
//
//    匹配一个回车符。等价于\x0d和\cM。
//
//        \s
//
//    匹配任何不可见字符，包括空格、制表符、换页符等等。等价于[ \f\n\r\t\v]。
//
//        \S
//
//    匹配任何可见字符。等价于[^ \f\n\r\t\v]。
//
//        \t
//
//    匹配一个制表符。等价于\x09和\cI。
//
//        \v
//
//    匹配一个垂直制表符。等价于\x0b和\cK。
//
//        \w
//
//    匹配包括下划线的任何单词字符。类似但不等价于“[A-Za-z0-9_]”，这里的"单词"字符使用Unicode字符集。
//
//        \W
//
//    匹配任何非单词字符。等价于“[^A-Za-z0-9_]”。
//
//        \xn
//
//    匹配n，其中n为十六进制转义值。十六进制转义值必须为确定的两个数字长。例如，“\x41”匹配“A”。“\x041”则等价于“\x04&1”。正则表达式中可以使用ASCII编码。
//
//        \num
//
//    匹配num，其中num是一个正整数。对所获取的匹配的引用。例如，“(.)\1”匹配两个连续的相同字符。
//
//        \n
//
//    标识一个八进制转义值或一个向后引用。如果\n之前至少n个获取的子表达式，则n为向后引用。否则，如果n为八进制数字（0-7），则n为一个八进制转义值。
//
//        \nm
//
//    标识一个八进制转义值或一个向后引用。如果\nm之前至少有nm个获得子表达式，则nm为向后引用。如果\nm之前至少有n个获取，则n为一个后跟文字m的向后引用。如果前面的条件都不满足，若n和m均为八进制数字（0-7），则\nm将匹配八进制转义值nm。
//
//        \nml
//
//    如果n为八进制数字（0-7），且m和l均为八进制数字（0-7），则匹配八进制转义值nml。
//
//        \ u n
//
//    匹配n，其中n是一个用四个十六进制数字表示的Unicode字符。例如，\u00A9匹配版权符号（&copy;）。
//
//        \< \>	匹配词（word）的开始（\<）和结束（\>）。例如正则表达式\<the\>能够匹配字符串"for the wise"中的"the"，但是不能匹配字符串"otherwise"中的"the"。注意：这个元字符不是所有的软件都支持的。
//        \( \)	将 \( 和 \) 之间的表达式定义为“组”（group），并且将匹配这个表达式的字符保存到一个临时区域（一个正则表达式中最多可以保存9个），它们可以用 \1 到\9 的符号来引用。
//        |	将两个匹配条件进行逻辑“或”（Or）运算。例如正则表达式(him|her) 匹配"it belongs to him"和"it belongs to her"，但是不能匹配"it belongs to them."。注意：这个元字符不是所有的软件都支持的。
//        +	匹配1或多个正好在它之前的那个字符。例如正则表达式9+匹配9、99、999等。注意：这个元字符不是所有的软件都支持的。
//        ?	匹配0或1个正好在它之前的那个字符。注意：这个元字符不是所有的软件都支持的。
//    {i} {i,j}	匹配指定数目的字符，这些字符是在它之前的表达式定义的。例如正则表达式A[0-9]{3} 能够匹配字符"A"后面跟着正好3个数字字符的串，例如A123、A348等，但是不匹配A1234。而正则表达式[0-9]{4,6} 匹配连续的任意4个、5个或者6个数字
}