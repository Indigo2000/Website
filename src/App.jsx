import * as React from 'react';
import { FaGithub, FaLinkedin, FaFilePdf } from 'react-icons/fa';
import { SiCodesignal } from 'react-icons/si';
import { FiSearch } from 'react-icons/fi';
import modules from './modules';
import './App.css'; // Ensure the CSS file is properly imported

const welcome = {
  greeting: 'Will Irvine - Software Developer',
  title: 'MSc in Computer Science Coursework'
};

const useStorageState = (key, initialState) => {
  const [value, setValue] = React.useState(localStorage.getItem(key) || initialState);

  React.useEffect(() => {
    localStorage.setItem(key, value);
  }, [value, key]);

  return [value, setValue];
};

const App = () => {
  const [searchTerm, setSearchTerm] = useStorageState('search', '');

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
  };

  const filteredModules = modules
    .map((section) => ({
      ...section,
      items: section.items ? section.items.filter((story) =>
        story.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
        (story.tags && story.tags.some(tag => tag.toLowerCase().includes(searchTerm.toLowerCase())))
      ) : [],
    }))
    .filter(section => section.items.length > 0);

  return (
    <div className="container">
      <header className="header">
        <h1>{welcome.greeting}</h1>
        <div className="social-links">
          <a href="/files/MyCV.pdf" download title="Download CV">
            <FaFilePdf />
          </a>
          <a href="https://github.com/Indigo2000" target="_blank" rel="noopener noreferrer" title="GitHub">
            <FaGithub />
          </a>
          <a href="https://www.linkedin.com/in/will-irvine-12675a236" target="_blank" rel="noopener noreferrer" title="LinkedIn">
            <FaLinkedin />
          </a>
          <a href="https://codesignal.com/learn/profile/cm5uvkicx001942mb7p3z8knt" target="_blank" rel="noopener noreferrer" title="CodeSignal">
            <SiCodesignal />
          </a>
        </div>
      </header>
      <main>
        <h2>{welcome.title}</h2>
        <InputWithLabel id="search" value={searchTerm} isFocused onInputChange={handleSearch} />
        {filteredModules.length > 0 ? <List sections={filteredModules} /> : <p>No matching items found.</p>}
      </main>
    </div>
  );
};

const List = ({ sections }) => (
  <div className="list-container">
    {sections.map((section, index) => (
      <div key={index} className="section">
        <h2>{section.heading}</h2>
        <ul>
          {section.items.map((item) => (
            <Item key={item.objectID} item={item} />
          ))}
        </ul>
      </div>
    ))}
  </div>
);

const Item = ({ item }) => (
  <li className="list-item">
    <a href={item.url} target="_blank" rel="noopener noreferrer">
      {item.title}
    </a>
  </li>
);

const InputWithLabel = ({ id, label, value, type = 'text', onInputChange, isFocused }) => {
  const inputRef = React.useRef();

  React.useEffect(() => {
    if (isFocused && inputRef.current) {
      inputRef.current.focus();
    }
  }, [isFocused]);

  return (
    <div className="search-container">
      <label htmlFor={id}>{label || 'Search'}</label>
      <div className="search-input-wrapper">
        <input 
          ref={inputRef} 
          id={id} 
          type={type} 
          value={value} 
          onChange={onInputChange} 
        />
        <FiSearch className="search-icon" />
      </div>
    </div>
  );
};

export default App;
