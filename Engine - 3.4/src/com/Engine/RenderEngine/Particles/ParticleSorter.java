package com.Engine.RenderEngine.Particles;

import java.util.List;

public class ParticleSorter {

	public static void sort(List<Particle> list) {
		if(list.isEmpty()) return;
		
//		Particle prev = list.get(0);
//		for(ListIterator<Particle> iter = list.listIterator(1); iter.hasNext();) {
//			Particle current = iter.next();
//			if(prev.getDistance() < current.getDistance()) {
//				iter.remove();
//				shiftDown(iter, current, iter.previousIndex());
//			}
//			
//			prev = current;
//		}
		
        for(int i = 1; i < list.size(); i++) {
            Particle particle = list.get(i);
            if (particle.getDistance() > list.get(i - 1).getDistance()) {
                sortUpHighToLow(list, i);
            }
        }
	}
	
//	public static void shiftDown(ListIterator<Particle> iter, Particle particle, int index) {
//		searchLoop: {
//			while(iter.hasPrevious()) {
//				if(iter.previous().getDistance() > particle.getDistance()) {
//					iter.add(particle);
//					break searchLoop;
//				}
//			}
//			
//			iter.add(particle);
//		}
//		
//		while(iter.nextIndex() < index) iter.next();
//	}
 
    private static void sortUpHighToLow(List<Particle> list, int i) {
        Particle particle = list.get(i);
        int attemptPos = i - 1;
        while (attemptPos != 0 && list.get(attemptPos - 1).getDistance() < particle.getDistance()) {
            attemptPos--;
        }
        
        list.remove(i);
        list.add(attemptPos, particle);
    }
}
