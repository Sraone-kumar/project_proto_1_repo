

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class justRefGenetic {

    int fac_size;
    int sec_size;
    int sub_size;
    int noOfHours;
    int NoofDays;



    //int fitness_limit;
    int population_size;
    int NumberOfBitMutations;
    float probability;
    int genome_length;
    int time_table_length;



    justRefGenetic(int fac_size,int sec_size,int sub_size,int noOfHours,int NoofDays)
    {
        this.fac_size = fac_size;
        this.sec_size = sec_size;
        this.sub_size = sub_size;
        this.noOfHours = noOfHours;
        this.NoofDays = NoofDays;
    }



    public ArrayList<SingleTimeTable> generate_populate(int population_size,int time_table_length,int genome_length,int fac,int sec,int sub, int time,int week)
    {
        ArrayList<SingleTimeTable> temp = new ArrayList<>();
        for(int i = 0;i<population_size;i++)
        {
            temp.add(new SingleTimeTable(time_table_length,genome_length,fac,sec,sub,time,week));
        }

        return temp;
    }


    //----------------------limit arguments funtions--------------------------

    int fitness(SingleTimeTable timeTable)
    {
        ArrayList<ArrayList<Genome>> totalSirList = new ArrayList<>();
        int TotalNoOfClashes = 0;

        for(int i = 0;i<fac_size;i++)
        {
        ArrayList<Genome> facWiseList = new ArrayList<>();
        for(int j = 0;j<timeTable.length;j++)
        {
            if(timeTable.TimeTable[j].gene[0] == i)
            {
                    facWiseList.add(timeTable.TimeTable[j]);
            }   
        }
        totalSirList.add(facWiseList);
        }

        

        for(int i = 0;i<totalSirList.size();i++)
        {
            //System.out.println("faculty: "+i+" total classes: "+totalSirList.get(i).size());
            TotalNoOfClashes += justReasearch.facultyClash(totalSirList.get(i));
        }

        return TotalNoOfClashes;
    }


    ArrayList<SingleTimeTable> PopulateFun()
    {
        return generate_populate(population_size, time_table_length, genome_length, fac_size, sec_size, sub_size, noOfHours, NoofDays);
    }


    SingleTimeTable MutationFunc(SingleTimeTable table)
    {
        return mutation(table, NumberOfBitMutations, probability);
    }





    //------------------------------------------------------------------------------

    ArrayList<SingleTimeTable> selection_pair(ArrayList<SingleTimeTable> population)
    {
        SingleTimeTable a = null,b = null;
        int[] weights = new int[population.size()];
        for(int i = 0;i<population.size();i++)
        {
            weights[i] = fitness(population.get(i));
        }

        int complete_weight = 0;
        for(int i = 0;i<weights.length;i++)
        {
            complete_weight += weights[i];
        }

        int r = (int)Math.random()*complete_weight;
        int s = (int)Math.random()*complete_weight;

        int countWeight = 0;

        for(int i = 0;i<weights.length;i++)
        {
            countWeight += weights[i];
            if(countWeight>=r)
            {
                a = population.get(i);
                countWeight = 0;
                break;
            }
                
        }

        for(int i = 0;i<weights.length;i++)
        {
            countWeight += weights[i];
            if(countWeight>=s)
            {
                b = population.get(i);
                countWeight = 0;
                break;
            }

        }



        return new ArrayList<SingleTimeTable>(Arrays.asList(a,b));
    }


    ArrayList<SingleTimeTable> single_point_crossover(SingleTimeTable a, SingleTimeTable b)
    {
        if(a.length!=b.length)
            throw new ArithmeticException("a and b should be same length");

        
        int len = a.genome_length;

        if(len<2)
            return new ArrayList<>(Arrays.asList(a,b));

        
        int min = 1,max = len-1;

        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);

        SingleTimeTable c = a,d = b;

        for(int i = 0;i<a.length;i++)
        {
            for(int j = 0;j<len;j++)
            {
                if(j<random_int)
                {
                    c.TimeTable[i].gene[j] = a.TimeTable[i].gene[j];
                    d.TimeTable[i].gene[j] = b.TimeTable[i].gene[j];
                }else
                {
                    c.TimeTable[i].gene[j] = b.TimeTable[i].gene[j];
                    d.TimeTable[i].gene[j] = a.TimeTable[i].gene[j];
                }
            }
        }

        return new ArrayList<>(Arrays.asList(c,d));
    }


    SingleTimeTable mutation(SingleTimeTable table, int NumberOfBitMutations, float probability)
    {
        for(int i = 0;i<NumberOfBitMutations;i++)
        {
            

            for(int j = 0;j<table.length;j++)
            {
                int index = new Random().nextInt(table.genome_length);
                int fac_rand = new Random().nextInt(fac_size);
                int sec_rand = new Random().nextInt(sec_size);
                int sub_rand = new Random().nextInt(sub_size);
                int hour_rand = new Random().nextInt(noOfHours);
                int week_rand = new Random().nextInt(NoofDays);

                switch(index)
                {
                    case 0:table.TimeTable[j].gene[index] = new 
                    Random().nextFloat()>probability?fac_rand:table.TimeTable[j].gene[index];
                    break;
                    case 1:table.TimeTable[j].gene[index] = new 
                    Random().nextFloat()>probability?sec_rand:table.TimeTable[j].gene[index];
                    break;
                    case 2:table.TimeTable[j].gene[index] = new 
                    Random().nextFloat()>probability?sub_rand:table.TimeTable[j].gene[index];
                    break;
                    case 3:table.TimeTable[j].gene[index] = new 
                    Random().nextFloat()>probability?hour_rand:table.TimeTable[j].gene[index];
                    break;
                    case 4:table.TimeTable[j].gene[index] = new 
                    Random().nextFloat()>probability?week_rand:table.TimeTable[j].gene[index];
                    break;
                }

            }

        }

        return table;
    }


    ArrayList<Object> run_evolution(int fitness_limit,int generation_limit)
    {
        ArrayList<SingleTimeTable> population = PopulateFun();
        int i;
        for(i = 0;i<generation_limit;i++)
        {
            Collections.sort(population,new Comparator<SingleTimeTable>(){
                @Override
                public int compare(SingleTimeTable a, SingleTimeTable b)
                {
                    return fitness(a)<fitness(b)?0:1;               }
            });

            if(fitness(population.get(0))<=fitness_limit)
            {
                System.out.println("fit_fun val: "+fitness(population.get(0)));
                System.out.println("fitness_limit: "+fitness_limit);
                break;
            }


            ArrayList<SingleTimeTable> next_generation = new ArrayList<>(Arrays.asList(population.get(0),population.get(1)));

            for(int j = 0;j<population_size/2-1;j++)
            {
                ArrayList<SingleTimeTable> parents = selection_pair(population);
                ArrayList<SingleTimeTable> OffSpringCrossOver = single_point_crossover(parents.get(0), parents.get(1));
                SingleTimeTable offSpring_a = OffSpringCrossOver.get(0);
                SingleTimeTable offSpring_b = OffSpringCrossOver.get(1);
                offSpring_a = MutationFunc(offSpring_a);
                offSpring_b = MutationFunc(offSpring_b);
                next_generation.add(offSpring_a);
                next_generation.add(offSpring_b);
            }

            population = next_generation;
        }
        
        System.out.println("i: (line 268): "+i);

        Collections.sort(population,new Comparator<SingleTimeTable>(){
            @Override
            public int compare(SingleTimeTable a, SingleTimeTable b)
            {
                return fitness(a)<fitness(b)?0:1;               }
        });

        return new ArrayList<Object>(Arrays.asList(population,i));
    }



}



