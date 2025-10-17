import React, { useState, useEffect } from 'react'
import { 
  ArrowRight, 
  BookOpen, 
  Code, 
  Database, 
  Zap, 
  Target, 
  Users, 
  Award,
  ChevronDown,
  Play,
  Star,
  CheckCircle
} from 'lucide-react'

/**
 * Modern Landing Page - Inspired by AWS Bedrock, Linear, Vercel, and Stripe
 * 
 * Features:
 * - Hero section with gradient mesh background
 * - Glass morphism cards
 * - Smooth micro-interactions
 * - Fluid typography and spacing
 * - Advanced animation system
 * - Responsive design
 */
const ModernLandingPage = () => {
  const [isVisible, setIsVisible] = useState(false)
  const [activeFeature, setActiveFeature] = useState(0)

  useEffect(() => {
    setIsVisible(true)
  }, [])

  const features = [
    {
      icon: BookOpen,
      title: "Comprehensive Learning",
      description: "Master Java, Spring, React, and System Design with our expertly crafted curriculum",
      gradient: "from-blue-500 to-purple-600"
    },
    {
      icon: Database,
      title: "Database Mastery",
      description: "SQL, NoSQL, optimization, and advanced database concepts with real-world examples",
      gradient: "from-green-500 to-teal-600"
    },
    {
      icon: Code,
      title: "Interactive Coding",
      description: "Practice with built-in code editor and execute your solutions in real-time",
      gradient: "from-orange-500 to-red-600"
    },
    {
      icon: Target,
      title: "Interview Ready",
      description: "2000+ questions from FAANG companies with detailed solutions and explanations",
      gradient: "from-purple-500 to-pink-600"
    }
  ]

  const stats = [
    { number: "2000+", label: "Interview Questions", icon: Target },
    { number: "8", label: "Learning Modules", icon: BookOpen },
    { number: "500+", label: "Code Examples", icon: Code },
    { number: "95%", label: "Success Rate", icon: Award }
  ]

  const testimonials = [
    {
      name: "Sarah Chen",
      role: "Senior Engineer at Google",
      content: "This platform helped me transition from mid-level to senior. The system design content is exceptional.",
      avatar: "SC",
      rating: 5
    },
    {
      name: "Michael Rodriguez",
      role: "Tech Lead at Amazon",
      content: "The database module alone is worth it. Comprehensive coverage from basics to advanced optimization.",
      avatar: "MR",
      rating: 5
    },
    {
      name: "Emily Johnson",
      role: "Full Stack Developer at Meta",
      content: "Interactive coding environment and real interview questions made all the difference in my preparation.",
      avatar: "EJ",
      rating: 5
    }
  ]

  return (
    <div className="modern-landing">
      {/* Modern Navigation */}
      <nav className="modern-nav">
        <ul>
          <li><a href="#features" className="active">Features</a></li>
          <li><a href="#curriculum">Curriculum</a></li>
          <li><a href="#testimonials">Reviews</a></li>
          <li><a href="#pricing">Pricing</a></li>
        </ul>
      </nav>

      {/* Hero Section */}
      <section className="hero-section">
        <div className="hero-content">
          <div className={`transition-all duration-1000 ${isVisible ? 'opacity-100 translate-y-0' : 'opacity-0 translate-y-8'}`}>
            <h1 className="hero-title">
              Master Senior-Level
              <br />
              <span className="text-gradient">Development Skills</span>
            </h1>
            <p className="hero-subtitle">
              The most comprehensive learning platform for Java, Spring, React, and System Design.
              <br />
              Prepare for FAANG interviews with confidence.
            </p>
            <div className="flex flex-col sm:flex-row gap-4 justify-center items-center">
              <button className="btn-modern btn-primary group">
                Start Learning
                <ArrowRight size={20} className="group-hover:translate-x-1 transition-transform" />
              </button>
              <button className="btn-modern btn-ghost group">
                <Play size={20} />
                Watch Demo
              </button>
            </div>
          </div>
        </div>

        {/* Floating Elements */}
        <div className="absolute top-20 left-10 floating-element">
          <div className="w-16 h-16 bg-blue-500/20 rounded-full backdrop-blur-sm"></div>
        </div>
        <div className="absolute top-40 right-20 floating-element">
          <div className="w-12 h-12 bg-purple-500/20 rounded-full backdrop-blur-sm"></div>
        </div>
        <div className="absolute bottom-40 left-20 floating-element">
          <div className="w-20 h-20 bg-green-500/20 rounded-full backdrop-blur-sm"></div>
        </div>
      </section>

      {/* Stats Section */}
      <section className="py-20 px-8">
        <div className="max-w-6xl mx-auto">
          <div className="modern-grid grid-cols-4">
            {stats.map((stat, index) => (
              <div key={index} className="glass-card text-center grid-item">
                <stat.icon size={32} className="mx-auto mb-4 text-blue-600" />
                <div className="text-3xl font-bold text-gradient mb-2">{stat.number}</div>
                <div className="text-neutral-600">{stat.label}</div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section id="features" className="py-20 px-8 bg-neutral-50">
        <div className="max-w-6xl mx-auto">
          <div className="text-center mb-16">
            <h2 className="text-4xl font-bold mb-6">
              Everything You Need to
              <span className="text-gradient"> Excel</span>
            </h2>
            <p className="text-xl text-neutral-600 max-w-3xl mx-auto">
              Our platform combines theoretical knowledge with practical implementation,
              providing everything needed to excel in technical interviews and real-world development.
            </p>
          </div>

          <div className="modern-grid">
            {features.map((feature, index) => (
              <div 
                key={index} 
                className="glass-card grid-item interactive-element"
                onMouseEnter={() => setActiveFeature(index)}
              >
                <div className={`w-12 h-12 rounded-xl bg-gradient-to-r ${feature.gradient} flex items-center justify-center mb-6`}>
                  <feature.icon size={24} className="text-white" />
                </div>
                <h3 className="text-xl font-semibold mb-4">{feature.title}</h3>
                <p className="text-neutral-600 leading-relaxed">{feature.description}</p>
                
                {/* Progress indicator */}
                <div className="mt-6">
                  <div className="progress-modern" data-progress={activeFeature === index ? "100" : "0"}></div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Learning Modules Preview */}
      <section className="py-20 px-8">
        <div className="max-w-6xl mx-auto">
          <div className="text-center mb-16">
            <h2 className="text-4xl font-bold mb-6">
              Comprehensive
              <span className="text-gradient"> Curriculum</span>
            </h2>
            <p className="text-xl text-neutral-600">
              8 expertly designed modules covering everything from fundamentals to advanced concepts
            </p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
            {[
              { name: "Java Fundamentals", progress: 85, color: "blue" },
              { name: "Spring Framework", progress: 70, color: "green" },
              { name: "React Development", progress: 90, color: "purple" },
              { name: "Database Systems", progress: 60, color: "orange" },
              { name: "Data Structures", progress: 95, color: "red" },
              { name: "Algorithms", progress: 80, color: "indigo" },
              { name: "System Design", progress: 45, color: "pink" },
              { name: "Interview Prep", progress: 75, color: "teal" }
            ].map((module, index) => (
              <div key={index} className="glass-card hover:scale-105 transition-all duration-300">
                <div className="flex items-center justify-between mb-4">
                  <h3 className="font-semibold">{module.name}</h3>
                  <CheckCircle size={20} className={`text-${module.color}-500`} />
                </div>
                <div className="progress-modern" data-progress={module.progress}></div>
                <div className="mt-2 text-sm text-neutral-600">{module.progress}% Complete</div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Testimonials */}
      <section id="testimonials" className="py-20 px-8 bg-neutral-50">
        <div className="max-w-6xl mx-auto">
          <div className="text-center mb-16">
            <h2 className="text-4xl font-bold mb-6">
              Trusted by
              <span className="text-gradient"> Developers</span>
            </h2>
            <p className="text-xl text-neutral-600">
              Join thousands of developers who've advanced their careers
            </p>
          </div>

          <div className="modern-grid grid-cols-3">
            {testimonials.map((testimonial, index) => (
              <div key={index} className="glass-card grid-item">
                <div className="flex items-center mb-4">
                  {[...Array(testimonial.rating)].map((_, i) => (
                    <Star key={i} size={16} className="text-yellow-400 fill-current" />
                  ))}
                </div>
                <p className="text-neutral-700 mb-6 leading-relaxed">"{testimonial.content}"</p>
                <div className="flex items-center">
                  <div className="w-10 h-10 bg-gradient-to-r from-blue-500 to-purple-600 rounded-full flex items-center justify-center text-white font-semibold text-sm mr-3">
                    {testimonial.avatar}
                  </div>
                  <div>
                    <div className="font-semibold">{testimonial.name}</div>
                    <div className="text-sm text-neutral-600">{testimonial.role}</div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="py-20 px-8">
        <div className="max-w-4xl mx-auto text-center">
          <div className="glass-card">
            <h2 className="text-4xl font-bold mb-6">
              Ready to
              <span className="text-gradient"> Level Up?</span>
            </h2>
            <p className="text-xl text-neutral-600 mb-8">
              Join the comprehensive learning platform trusted by senior developers worldwide
            </p>
            <div className="flex flex-col sm:flex-row gap-4 justify-center">
              <button className="btn-modern btn-primary">
                Start Your Journey
                <ArrowRight size={20} />
              </button>
              <button className="btn-modern btn-ghost">
                Explore Curriculum
              </button>
            </div>
          </div>
        </div>
      </section>
    </div>
  )
}

export default ModernLandingPage