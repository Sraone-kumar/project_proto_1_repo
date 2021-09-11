

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.*;

class Genome{
    int length;
    int[] arr = new int[length];
    Genome(int[] arr,int length)
    {
        this.arr = arr;
        this.length = length;
    }

    String get()
    {
        return Arrays.toString(arr);
    }
}

class Things{
    String name;
    int value;
    int weight;
    Things(String name, int value, int weight)
    {
        this.name = name;
        this.value = value;
        this.weight = weight;
    }

    ArrayList<Object> get()
    {
        return new ArrayList<Object>(Arrays.asList(name,value,weight));
    }
    
    
     
}


// class SortByFitness extends project implements Comparator<Genome>
// {
//     public int compare(Genome a,Genome b)
//     {
//         int val1,val2;
//         val1 =fitness_fun(a);
//         val2 = fitness_fun(b);

//         if(val1 == val2)
//             return 0;
//         else if(val1<val2)
//             return 1;
//         else
//             return -1;
//     }

// }









public class project {

    int weight_limit;
    ArrayList<Things> thing;
    //ArrayList<Genome> population;
    int genome_length;
    int population_size;
    int NumberOfBitMutations;
    float probability;
    //Genome genome;

    project()
    {
        thing = new ArrayList<>();
    }




    public static Genome generate_genome(int length)
    {
        int[] list = new int[length];
        for(int i = 0;i<length;i++)
        {
            list[i] = Math.random()<0.5?0:1;
        }
        return new Genome(list, length);
    }

    public static ArrayList<Genome> generate_population(int population_size,int genome_length)
    {
        ArrayList<Genome> temp = new ArrayList<>();
        for(int i = 0;i<population_size;i++)
        {
            temp.add(generate_genome(genome_length));
        }

        return temp;
    }


    int fitness(Genome gene,ArrayList<Things> thing,int weight_limit)
    {

        if(gene.length!= thing.size())
            throw new ArrayIndexOutOfBoundsException("Crazy thing happened"+gene.length+", "+thing.size());

        int weight = 0;
        int value = 0; 

        for(int i = 0;i<thing.size();i++)
        {
            if(gene.arr[i] == 1)
            {
                weight+=thing.get(i).weight;
                value+=thing.get(i).value;

                if(weight>weight_limit)
                {
                    return 0;
                }
            }
        }

        return value;
        
    }

//--------------------functions as parameter----------------------------------
    int fitness_fun(Genome genome)
    {
        return fitness(genome, thing, weight_limit);
    }

    ArrayList<Genome> PopulateFun()
    {
        return generate_population(population_size, genome_length);
    }

    // ArrayList<Genome> SelectionFun(ArrayList<Genome> population)
    // {
    //     return selection_pair(population);
    // }

    Genome MutationFunc(Genome genome)
    {
        return mutation(genome, NumberOfBitMutations, probability);
    }


//-----------------------------------------------------    

    ArrayList<Genome> selection_pair(ArrayList<Genome> population)
    {
        Genome a=null,b = null;
        int[] weights = new int[population.size()];
        for(int i = 0;i<population.size();i++)
        {
            weights[i] = fitness_fun(population.get(i));
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

        return new ArrayList<Genome>(Arrays.asList(a,b));


    }


    ArrayList<Genome> single_point_crossover(Genome a,Genome b)
    {
        if(a.length != b.length)
        {
            throw new ArithmeticException("a and b should be same length");
        }

        int length = a.length;

        if(length<2)
            return new ArrayList<>(Arrays.asList(a,b));

        int min = 1,max = a.length-1;

        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        //System.out.println("rand_int: "+random_int);
        
        Genome c = a,d = b;
        for(int i = 0;i<a.length;i++)
        {
            if(i<random_int)
            {
                c.arr[i] = a.arr[i];
                d.arr[i] = b.arr[i];
            }else
            {
                c.arr[i] = b.arr[i];
                d.arr[i] = a.arr[i];
            }
        }

        return new ArrayList<Genome>(Arrays.asList(c,d));
        
        
    }

    Genome mutation(Genome genome,int NumberOfBitMutations, float probability)
    {
        for(int i = 0;i<NumberOfBitMutations;i++)
        {
            int index = new Random().nextInt(genome.length);
            genome.arr[index] = new Random().nextFloat()>probability?genome.arr[index]:Math.abs(genome.arr[index]-1);
        }
        return genome;
    }





    ArrayList<Object> run_evolution(int fitness_limit,int generation_limit)
    {
        ArrayList<Genome> population = PopulateFun();
        // System.out.println("generation limit :"+generation_limit);
        int i;
        for(i = 0;i<generation_limit;i++)
        {
            // System.out.println("here");
            Collections.sort(population,new Comparator<Genome>(){
                @Override
                public int compare(Genome a,Genome b)
                {
                    return fitness_fun(a)<fitness_fun(b)?0:1;
                }
            });
            if(fitness_fun(population.get(0))>=fitness_limit)
            {
                System.out.println("fit_fun val: "+fitness_fun(population.get(0)));
                System.out.println("fitness_limit: "+fitness_limit);
                break;
            }
                
            
            ArrayList<Genome> next_generation = new ArrayList<>(Arrays.asList(population.get(0),population.get(1)));

            for(int j = 0;j<population.size()/2-1;j++)
            {
                ArrayList<Genome> parents = selection_pair(population);
                ArrayList<Genome> OffSpringCrossOver = single_point_crossover(parents.get(0), parents.get(1));
                Genome offSpring_a = OffSpringCrossOver.get(0);
                Genome offSpring_b = OffSpringCrossOver.get(1);
                offSpring_a = MutationFunc(offSpring_a);
                offSpring_b = MutationFunc(offSpring_b);
                next_generation.add(offSpring_a);
                next_generation.add(offSpring_b);
            }

            population = next_generation;

        }

        System.out.println(i);

        Collections.sort(population,new Comparator<Genome>(){
            @Override
            public int compare(Genome a,Genome b)
            {
                return fitness_fun(a)<fitness_fun(b)?0:1;
            }
        });

        return new ArrayList<Object>(Arrays.asList(population,i));

    }


    public static void main(String[] args) {


        project proj = new project();


        ArrayList<Things> more_things = new ArrayList<>(Arrays.asList(
            new Things("mints", 5, 25),
            new Things("socks", 10, 38),
            new Things("Tissues", 15, 80),
            new Things("phone",500,200),
            new Things("Baseball Cap", 100, 70)
        ));


        proj.thing.add(new Things("laptop", 500, 2200));
        proj.thing.add(new Things("HeadPhones", 150, 160));
        proj.thing.add(new Things("Coffe Mug", 60, 350));
        proj.thing.add(new Things("Notepad", 40, 333));
        proj.thing.add(new Things("Water Bottle", 30, 192));
        proj.thing.addAll(more_things);

        proj.weight_limit = 3000;
        //ArrayList<Genome> population;
        proj.genome_length = proj.thing.size();
        proj.population_size = 20;
        proj.NumberOfBitMutations = 3;
        proj.probability = 0.5f;

        int fitness_limit = 1310;
        int generation_limit = 500; 


        //System.out.println(proj.thing.size());
        //ArrayList<Genome> population = proj.PopulateFun();
        // Collections.sort(population,new Comparator<Genome>(){
        //     @Override
        //     public int compare(Genome a,Genome b)
        //     {
        //         return proj.fitness_fun(a)<proj.fitness_fun(b)?0:1;
        //     }
        // });

        // for(int i = 0;i<population.size();i++)
        // {
        //     System.out.println(proj.fitness_fun(population.get(i)));
        // }
        

        ArrayList<Object> Pop_Gen = proj.run_evolution(fitness_limit, generation_limit);

        ArrayList<Genome> outCome = new ArrayList<>((Collection<Genome>)Pop_Gen.get(0));
        System.out.println("best Solution"+outCome.get(0).get());
        System.out.println("Generations:"+(int)Pop_Gen.get(1));
        
        int fit_val = 0,final_weight = 0;
        for(int i = 0;i<outCome.get(0).arr.length;i++)
        {
            if(outCome.get(0).arr[i] == 1)
            {
                final_weight += proj.thing.get(i).weight;
                fit_val += proj.thing.get(i).value;
                System.out.println(proj.thing.get(i).get());

            }
                
        }

        System.out.println("final_fit_val: "+fit_val);
        System.out.println("final_weight: "+final_weight);

    }
    
}



