import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

class Facul{
    String name;
    int noClassPerWeek;
    int id;
    Facul(String name,int noClassPerWeek,int id)
    {
        this.name = name;
        this.noClassPerWeek = noClassPerWeek;
        this.id = id; 
    }

    public String toString()
    {
        return "["+this.name+","+String.valueOf(this.noClassPerWeek)+","+String.valueOf(this.id)+"]";
    }

}

class Sections{
   String name;
   int order;
   Sections(String name,int order)
   {
       this.name = name;
       this.order = order;
   }

   public String toString()
    {
        return "["+this.name+","+String.valueOf(this.order)+"]";
    }
}

class Subject
{
    String name;
    int id;
    Subject(String name,int id)
    {
        this.name = name;
        this.id = id;
    }

    public String toString()
    {
        return "["+this.name+","+String.valueOf(this.id)+"]";
    }
}

class HourTime{
    int time;
    HourTime(int time)
    {
        this.time = time;
    }

    public String toString()
    {
        return "["+String.valueOf(this.time)+"]";
    }
}

class WeekDay
{
    String weekOfday;
    int val;
    WeekDay(String weekOfday,int val)
    {
        this.weekOfday = weekOfday;
        this.val = val;
    }

    public String toString()
    {
        return "["+this.weekOfday+","+String.valueOf(this.val)+"]";
    }

}

class Genome
{
    int length;
    int[] gene;
    Genome(int length)
    {
        this.length = length;
        gene = new int[length];
    }

    Genome(int[] gene)
    {
        this.gene = gene;
        length = gene.length;
    }

    String get()
    {
        return Arrays.toString(gene);
    }
}

class GenerateRandomGene
{
    // ArrayList<Facul> faculty;
    // ArrayList<Sections> section;
    // ArrayList<Subject> subjects;
    // ArrayList<HourTime> hour;
    // ArrayList<WeekDay> week;
    int facSize,sectionSize,subjectsSize,TotalHours,NoOfWeekdays;

    GenerateRandomGene(int facSize,int sectionSize,int subjectsSize,int TotalHours,int NoOfWeekdays){
        this.facSize = facSize;
        this.sectionSize = sectionSize;
        this.subjectsSize = subjectsSize;
        this.TotalHours = TotalHours;
        this.NoOfWeekdays = NoOfWeekdays;
    }


    Genome RandomeGeneGenerator(int lengthOfGene)
    {
        int rand;
        Genome gene = new Genome(lengthOfGene);
        for(int i = 0;i<lengthOfGene;i++)
        {
            switch(i)
            {
                case 0:rand = new Random().nextInt(facSize);
                gene.gene[i] = rand;
                break;
                case 1:rand = new Random().nextInt(sectionSize);
                gene.gene[i] = rand;
                break;
                case 2:rand = new Random().nextInt(subjectsSize);
                gene.gene[i] = rand;
                break;
                case 3:rand = new Random().nextInt(TotalHours);
                gene.gene[i] = rand;
                break;
                case 4:rand = new Random().nextInt(NoOfWeekdays);
                gene.gene[i] = rand;
                break;
                default:
                    break;
            }
            
        }

        return gene;
    }

}

class SingleTimeTable
{
    
    int length,genome_length,fac,sec,sub,time,week;
    Genome[] TimeTable;
    SingleTimeTable(int length,int genome_length,int fac,int sec,int sub,int time, int week)
    {
        TimeTable = new Genome[length];
        this.length = length;
        this.genome_length = genome_length;
        this.fac = fac;
        this.sub  = sub;
        this.week = week;
        for(int i = 0;i<length;i++)
       {
           TimeTable[i] = new GenerateRandomGene(fac, sec, sub, time, week).RandomeGeneGenerator(genome_length);
       }
        
    }
}








public class justReasearch {


    static int facultyClash(ArrayList<Genome> facWiseList)
    {
        int TotalClashes = 0;

        for(int i = 0;i<facWiseList.size();i++)
        {
            ArrayList<Genome> temp = new ArrayList<>();
            for(int j = 0;j<facWiseList.size();j++)
            {
                if(facWiseList.get(j).gene[3] == i)
                {
                    temp.add(facWiseList.get(j));
                }
            }
 
            HashMap<Integer,ArrayList<Genome>> maper = new HashMap<>();
 
            for(int k = 0;k<temp.size();k++)
            {
                if(!maper.containsKey(temp.get(k).gene[4]))
                {
                    maper.put(temp.get(k).gene[4], new ArrayList<>());
                    maper.get(temp.get(k).gene[4]).add(temp.get(k));
                }else
                {
                    maper.get(temp.get(k).gene[4]).add(temp.get(k));
                }
            }
 
            Collection<ArrayList<Genome>> values = maper.values();
 
            for (ArrayList<Genome> arrayList : values) {
                if(arrayList.size()>1)
                {
                     //System.out.println("--------------clash----------------");
                     TotalClashes+=1;
                    //  for(int p = 0;p<arrayList.size();p++)
                    //  {
                    //      System.out.println(arrayList.get(p).get());
                    //  }
                }
            }
            maper.clear();
        }
 
        //System.out.println("TotalClashes: "+TotalClashes);

        return TotalClashes;
    }




    static void table_printer(SingleTimeTable table,ArrayList<Facul> fac,ArrayList<Sections> sec,ArrayList<Subject> sub, ArrayList<HourTime> time, ArrayList<WeekDay> week)
    {
        for(int i = 0;i<table.length;i++)
        {
            int id = i,timer=0;
            String fac_string = null, sec_string = null, sub_string = null,week_string = null;

            System.out.print("[ ");
            for(int j = 0;j<table.genome_length;j++)
            {
                switch(j)
                {
                    case 0:
                    System.out.print(fac.get(table.TimeTable[i].gene[j]).name+", ");
                    fac_string = fac.get(table.TimeTable[i].gene[j]).name;
                    break;
                    case 1:
                    System.out.print(sec.get(table.TimeTable[i].gene[j]).name+", ");
                    sec_string = sec.get(table.TimeTable[i].gene[j]).name;
                    break;
                    case 2:
                    System.out.print(sub.get(table.TimeTable[i].gene[j]).name+", ");
                    sub_string = sub.get(table.TimeTable[i].gene[j]).name;
                    break;
                    case 3:
                    System.out.print(time.get(table.TimeTable[i].gene[j]).time+", ");
                    timer = time.get(table.TimeTable[i].gene[j]).time;
                    break;
                    case 4:
                    System.out.print(week.get(table.TimeTable[i].gene[j]).weekOfday+", ");
                    week_string = week.get(table.TimeTable[i].gene[j]).weekOfday;
                    break;
                    default:
                    break;
                }
                
            }

            System.out.print(" ]");
                System.out.println();

            try {
                new jdbc_connector().InsertIntoTable(id, fac_string, sec_string, sub_string, timer, week_string);
            } catch (Exception e) {
                System.out.println(e);
            }    
            

        }
    }



    public static void main(String[] args) {

        int timeTableLength;
        int genome_length;

        // int fac_size;
        // int sec_size;
        // int sub_size;
        // int noOfHours;
        // int NoofDays;



        int fitness_limit = 7;
        int population_size = 30;
        int NumberOfBitMutations = 1;
        float probability = 0.5f;
        int generation_limit = 100;





        ArrayList<Facul> fac = new ArrayList<>(Arrays.asList(
            new Facul("a", 5, 0),
            new Facul("b", 5, 1),
            new Facul("c", 5, 2),
            new Facul("d", 5, 3),
            new Facul("e", 5, 4)
        ));

        ArrayList<Sections> sec = new ArrayList<>(Arrays.asList(
            new Sections("cse1", 0),
            new Sections("cse2", 1),
            new Sections("cse3", 2)
        ));

        ArrayList<Subject> sub = new ArrayList<>(Arrays.asList(
            new Subject("Maths", 0),
            new Subject("science", 1),
            new Subject("History",2),
            new Subject("physics", 3),
            new Subject("chemistry", 4)
        ));

        ArrayList<HourTime> time = new ArrayList<>(Arrays.asList(
            new HourTime(0),
            new HourTime(1),
            new HourTime(2),
            new HourTime(3),
            new HourTime(4)
        ));

        ArrayList<WeekDay> week = new ArrayList<>(Arrays.asList(
            new WeekDay("Monday", 1),
            new WeekDay("Tuesday", 2),
            new WeekDay("Wednesday", 3),
            new WeekDay("thursday", 4),
            new WeekDay("friday", 5)
        ));


        timeTableLength = sec.size()*time.size()*week.size();
        genome_length = 5;

        justRefGenetic algo = new justRefGenetic(fac.size(),
         sec.size(), sub.size(), time.size()   , week.size());

        algo.population_size = population_size;
        //algo.fitness_limit = fitness_limit;
        algo.NumberOfBitMutations = NumberOfBitMutations;
        algo.probability = probability;
        algo.time_table_length = timeTableLength;
        algo.genome_length = genome_length;


        ArrayList<Object> obj = algo.run_evolution(fitness_limit, generation_limit);

        ArrayList<SingleTimeTable> population_of_tables = new ArrayList<>((ArrayList<SingleTimeTable>)obj.get(0));
        System.out.println("fitness: "+algo.fitness(population_of_tables.get(0)));

        table_printer(population_of_tables.get(0), fac, sec, sub, time, week);
        












       // Genome myGene = new GenerateRandomGene(fac, sec, sub, time, week);
       //Genome[] gene = new Genome[timeTableLength];
       



       //.....................class calc.....code............
        // SingleTimeTable timeTable = new SingleTimeTable(timeTableLength, genome_length, fac.size(),sec.size(),
        //  sub.size(), time.size(), week.size());



        
    //    ArrayList<ArrayList<Genome>> totalSirList = new ArrayList<>();

    //    for(int i = 0;i<fac.size();i++)
    //    {
    //     ArrayList<Genome> facWiseList = new ArrayList<>();
    //     for(int j = 0;j<timeTableLength;j++)
    //     {
    //         if(timeTable.TimeTable[j].gene[0] == i)
    //         {
    //              facWiseList.add(timeTable.TimeTable[j]);
    //         }   
    //     }
    //     totalSirList.add(facWiseList);
    //    }

    //    int TotalNoOfClashes = 0;

    //    for(int i = 0;i<totalSirList.size();i++)
    //    {
    //        System.out.println("faculty: "+i+" total classes: "+totalSirList.get(i).size());
    //        TotalNoOfClashes += facultyClash(totalSirList.get(i));
    //    }

    //    System.out.println("Total no of Clashes: "+TotalNoOfClashes);


       

       







        //System.out.println(myGene.get());
        //System.out.println(myGene.length);

        // for(int i = 0;i<myGene.length;i++)
        // {
        //     switch(i)
        //     {
        //         case 0:
        //         System.out.println(fac.get(myGene.gene[i])); 
        //         break;
        //         case 1:System.out.println(sec.get(myGene.gene[i]).toString());
        //         break;
        //         case 2:System.out.println(sub.get(myGene.gene[i]).toString());
        //         break;
        //         case 3:System.out.println(time.get(myGene.gene[i]).toString());
        //         break;
        //         case 4:System.out.println(week.get(myGene.gene[i]).toString());
        //         break;
        //         default:
        //             break;
        //     }
            
        // }



        
    }
}
